package com.example.android.notepad;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.notepad.cp.NotepadContentProvider;
import com.example.android.notepad.db.NotepadTable;

/*
 * notepadDetailActivity allows to enter a new notepad item 
 * or to change an existing
 */
public class DetailActivity extends Activity {
	private Spinner mCategory;
	private EditText mTitleText; 
	private EditText mBodyText;

	private Uri notepadUri;

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.notepad_edit);

		mCategory = (Spinner) findViewById(R.id.category);
		mTitleText = (EditText) findViewById(R.id.notepad_edit_summary);
		mBodyText = (EditText) findViewById(R.id.notepad_edit_description);
		Button confirmButton = (Button) findViewById(R.id.notepad_edit_button);

		Bundle extras = getIntent().getExtras();

		// Check from the saved Instance
		notepadUri = (bundle == null) ? null : (Uri) bundle
				.getParcelable(NotepadContentProvider.CONTENT_ITEM_TYPE);

		// Or passed from the other activity
		if (extras != null) {
			notepadUri = extras
					.getParcelable(NotepadContentProvider.CONTENT_ITEM_TYPE);

			fillData(notepadUri);
		}

		confirmButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				if (TextUtils.isEmpty(mTitleText.getText().toString())) {
					makeToast();
				} else {
					setResult(RESULT_OK);
					finish();
				}
			}

		});
		
	}

	private void fillData(Uri uri) {
		String[] projection = { NotepadTable.COLUMN_SUMMARY,
				NotepadTable.COLUMN_DESCRIPTION, NotepadTable.COLUMN_CATEGORY };
		Cursor cursor = getContentResolver().query(uri, projection, null, null,
				null);
		if (cursor != null) {
			cursor.moveToFirst();
			String category = cursor.getString(cursor
					.getColumnIndexOrThrow(NotepadTable.COLUMN_CATEGORY));

			for (int i = 0; i < mCategory.getCount(); i++) {

				String s = (String) mCategory.getItemAtPosition(i);
				if (s.equalsIgnoreCase(category)) {
					mCategory.setSelection(i);
				}
			}

			mTitleText.setText(cursor.getString(cursor
					.getColumnIndexOrThrow(NotepadTable.COLUMN_SUMMARY)));
			mBodyText.setText(cursor.getString(cursor
					.getColumnIndexOrThrow(NotepadTable.COLUMN_DESCRIPTION)));

			// Always close the cursor
			cursor.close();
		}
	}

	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		saveState();
		outState.putParcelable(NotepadContentProvider.CONTENT_ITEM_TYPE, notepadUri);
	}

	@Override
	protected void onPause() {
		super.onPause();
		saveState();
	}

	private void saveState() {
		String category = (String) mCategory.getSelectedItem();
		String summary = mTitleText.getText().toString();
		String description = mBodyText.getText().toString();

		// Only save if either summary or description
		// is available

		if (description.length() == 0 && summary.length() == 0) {
			return;
		}

		ContentValues values = new ContentValues();
		values.put(NotepadTable.COLUMN_CATEGORY, category);
		values.put(NotepadTable.COLUMN_SUMMARY, summary);
		values.put(NotepadTable.COLUMN_DESCRIPTION, description);

		if (notepadUri == null) {
			// New note
			notepadUri = getContentResolver().insert(
					NotepadContentProvider.CONTENT_URI, values);
		} else {
			// Update Notepad
			getContentResolver().update(notepadUri, values, null, null);
		}
	}

	private void makeToast() {
		Toast.makeText(DetailActivity.this, "Please enter a summary",
				Toast.LENGTH_LONG).show();
	}
}
