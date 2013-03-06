package com.example.favorites;

import java.util.Arrays;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;

// main Activity class for the Favorite app
public class Favorites extends Activity 
{
   private EditText urlEditText; // where the user enters url
   private EditText listEditText; // where the user enters a name for the url
   private SharedPreferences savedSiteList; // user's favorite sites
   private TableLayout listTableLayout; // shows the search buttons

   // called when the activity is first created
   @Override
   public void onCreate(Bundle savedInstanceState) 
   {
      super.onCreate(savedInstanceState); // call the superclass version
      setContentView(R.layout.main); // set the layout
      
      // get the SharedPreferences that contains the user's saved searches 
      savedSiteList = getSharedPreferences("searches", MODE_PRIVATE);

      // get a reference to the listTableLayout
      listTableLayout = 
         (TableLayout) findViewById(R.id.listTableLayout);
      
      // get references to the two EditTexts  
      urlEditText = (EditText) findViewById(R.id.urlEditText);
      listEditText = (EditText) findViewById(R.id.listEditText);
      
      // register listeners for the Save and Clear Lists Buttons
      Button saveButton = (Button) findViewById(R.id.saveButton);
      saveButton.setOnClickListener(saveButtonListener);
      Button clearListsButton = 
        (Button) findViewById(R.id.clearListsButton);
      clearListsButton.setOnClickListener(clearListsButtonListener);
      
      reloadButtons(null); // add previously saved searches to UI
   } 
   
   // recreates search url name and edit Buttons for all saved searches;
   // passes null to create all the url name and edit Buttons.
   private void reloadButtons(String newList) 
   {
      // store saved url/url names in the lists array
      String[] lists = 
         savedSiteList.getAll().keySet().toArray(new String[0]); 
      Arrays.sort(lists, String.CASE_INSENSITIVE_ORDER); // sort by list

      // if a new list was added, insert in GUI at the appropriate location
      if (newList != null)
      {
         makeListGUI(newList, Arrays.binarySearch(lists, newList));
      }
      else // display GUI for all lists
      {         
         // display all saved searches
         for (int index = 0; index < lists.length; ++index)
            makeListGUI(lists[index], index);
      } 
   } 

   // add new search to the save file, then reload all Buttons
   private void makeList(String url, String site_name)
   {
      // originalSiteList will be null if we're modifying an existing search
      String originalSiteList = savedSiteList.getString(site_name, null);

      // get a SharedPreferences.Editor to store new url/site name pair
      SharedPreferences.Editor preferencesEditor = savedSiteList.edit();
      preferencesEditor.putString(site_name, url); // store current search
      preferencesEditor.apply(); // store the updated preferences
            
      // if this is a new site name, add its GUI
      if (originalSiteList == null) 
         reloadButtons(site_name); // adds a new button for this list
   } 
   
   // add a new list button and corresponding edit button to the GUI
   private void makeListGUI(String list, int index)
   {
      // get a reference to the LayoutInflater service
      LayoutInflater inflater = (LayoutInflater) getSystemService(
         Context.LAYOUT_INFLATER_SERVICE);

      // inflate new_list_view.xml to create new list and edit Buttons
      View newListView = inflater.inflate(R.layout.new_list_view, null);
       
      // get newListButton, set its text and register its listener
      Button newListButton = 
         (Button) newListView.findViewById(R.id.newListButton);
      newListButton.setText(list); 
      newListButton.setOnClickListener(listButtonListener); 

      // get newEditButton and register its listener
      Button newEditButton = 
         (Button) newListView.findViewById(R.id.newEditButton); 
      newEditButton.setOnClickListener(editButtonListener);

      // add new list and edit buttons to listTableLayout
      listTableLayout.addView(newListView, index);
   } 
   
   // remove all saved search Buttons from the app
   private void clearButtons()
   {
      // remove all saved search Buttons
      listTableLayout.removeAllViews();
   } 
   
   // create a new Button and add it to the ScrollView
   public OnClickListener saveButtonListener = new OnClickListener() 
   {
      @Override
      public void onClick(View v) 
      {
         // create list if both urlEditText and listEditText are not empty
         if (urlEditText.getText().length() > 0 &&
            listEditText.getText().length() > 0)
         {
            makeList(urlEditText.getText().toString(), 
               listEditText.getText().toString());
            urlEditText.setText(""); // clear urlEditText
            listEditText.setText(""); // clear listEditText
            
            // hide the soft keyboard
            ((InputMethodManager) getSystemService(
               Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
               listEditText.getWindowToken(), 0);  
         } 
         else // display message asking user to provide a site_name and a url
         {
            // create a new AlertDialog Builder
            AlertDialog.Builder builder = 
               new AlertDialog.Builder(Favorites.this);

            builder.setTitle(R.string.missingTitle); // title bar string

            // provide an OK button that simply dismisses the dialog
            builder.setPositiveButton(R.string.OK, null); 
            
            // set the message to display
            builder.setMessage(R.string.missingMessage);
            
            // create AlertDialog from the AlertDialog.Builder
            AlertDialog errorDialog = builder.create();
            errorDialog.show(); // display the Dialog
         } 
      } 
   }; 

   // clears all saved searches
   public OnClickListener clearListsButtonListener = new OnClickListener() 
   {
      @Override
      public void onClick(View v) 
      {
         // create a new AlertDialog Builder
         AlertDialog.Builder builder = 
            new AlertDialog.Builder(Favorites.this);

         builder.setTitle(R.string.confirmTitle); // title bar string

         // provide an OK button that simply dismisses the dialog
         builder.setPositiveButton(R.string.erase,
            new DialogInterface.OnClickListener()
            {
               @Override
               public void onClick(DialogInterface dialog, int button)
               {
                  clearButtons(); // clear all saved searches from the map
                  
                  // get a SharedPreferences.Editor to clear searches
                  SharedPreferences.Editor preferencesEditor = 
                     savedSiteList.edit();
                  
                  preferencesEditor.clear(); // remove all url name/site_name pairs
                  preferencesEditor.apply(); //  the changes
               } 
            } 
         );
         
         builder.setCancelable(true);
         builder.setNegativeButton(R.string.cancel, null);
         
         // set the message to display
         builder.setMessage(R.string.confirmMessage);
         
         // create AlertDialog from the AlertDialog.Builder
         AlertDialog confirmDialog = builder.create();
         confirmDialog.show(); // display the Dialog
      } 
   }; 

   // load selected search in a web browser
   public OnClickListener listButtonListener = new OnClickListener()
   {
      @Override
      public void onClick(View v) 
      {
         // get the url
         String buttonText = ((Button)v).getText().toString();
         String url = savedSiteList.getString(buttonText, "");
         
         // create the URL corresponding to the touched Button's site name
         String urlString = getString(R.string.searchURL) + url;

         // create an Intent to launch a web browser    
         Intent webIntent = new Intent(Intent.ACTION_VIEW, 
            Uri.parse(urlString));                      

         startActivity(webIntent); // execute the Intent
      } 
   }; 
   
   // edit selected search
   public OnClickListener editButtonListener = new OnClickListener()
   {
      @Override
      public void onClick(View v) 
      {
         // get all necessary GUI components
         TableRow buttonTableRow = (TableRow) v.getParent();
         Button searchButton = 
            (Button) buttonTableRow.findViewById(R.id.newListButton);
         
         String list = searchButton.getText().toString();
         
         // set EditTexts to match the chosen url and site_name
         listEditText.setText(list);
         urlEditText.setText(savedSiteList.getString(list, ""));
      } 
   }; 
} 