package com.example.fragmentapplication;

import java.util.List;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends Activity {

	public ItemDataSource datasource;
	public static MainActivity MAIN;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_fragment);
        
        MainActivity.MAIN = this;
        
        datasource = new ItemDataSource(this);
        datasource.open();
        
        List<String> all = datasource.getAllItems();
        
        System.out.println("------------ Printing items");
        for(int i = 0; i < all.size() ; i++){
        	System.out.println("Item :> " + all.get(i));
        }
        System.out.println("------------ End Printing items");
        
        //loadDataSet(datasource);
        
    }

    
    public void loadDataSet(ItemDataSource source){
    	
    	for( int i = 0; i < 10 ; i++){
    		datasource.insertItem(i);
    	}
    	
    }
    @Override
    public void onPause(){
    	datasource.close();
    	super.onPause();
    }
    
    public void onResume(){
    	datasource.open();
    	super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    
    public static class DetailsFragment extends Fragment{
    	
    	public static DetailsFragment newInstance(int index){
    		DetailsFragment f = new DetailsFragment();
    		Bundle args = new Bundle();
    		args.putInt("index", index);
    		f.setArguments(args);
    		return f;
    	}
    	public int getShownIndex(){
    		return getArguments().getInt("index", 0);
    	}
    	@Override
    	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){	
    		if(container == null){
    			return null;
    		}
    		ScrollView scroller = new ScrollView( getActivity() );
    		TextView text = new TextView( getActivity() );
    		int padding = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, 
    											getActivity().getResources().getDisplayMetrics() );
    		text.setPadding(padding,padding,padding,padding);		
    		scroller.addView(text);
    		text.setText("hello " + getShownIndex() );
    		return scroller;
    	}

    }
    
    public static class TitlesFragment extends ListFragment{
    	
    	boolean mDualPane;
    	int mCurCheckPosition = 0;
    	
    	@Override
    	public void onActivityCreated(Bundle savedInstanceState){
    		super.onActivityCreated(savedInstanceState);
    		
    		//String titles[] = {"1" , "2" , "3"}; 
    		List<String> t = (MainActivity.MAIN.datasource.getAllItems());
    		
    		setListAdapter(new ArrayAdapter<String>(getActivity() , 
    						android.R.layout.simple_list_item_activated_1,
    						t) 
    					  );
    		
    		//View detailsFrame = getActivity().findViewById(R.id.details);
    		mDualPane = true;
    		
    		if( savedInstanceState != null)
    			mCurCheckPosition = savedInstanceState.getInt("curChoice" , 0 );
    		
    		
    		if( mDualPane){
    			getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    			showDetails(mCurCheckPosition);
    			
    		}
    		
    	}
    	
    	
    	 @Override
    	 public void onSaveInstanceState(Bundle outState){
    		   
    		   super.onSaveInstanceState(outState);
    		   outState.putInt("curChoince", mCurCheckPosition);
    		   
    	 }
    	
    	@Override
    	public void onListItemClick(ListView l, View v, int position, long id){
    		showDetails(position);
    	}
    	
    	void showDetails(int index){
    		mCurCheckPosition = index;
    		
    		//if( mDualPane ){
    			
    			getListView().setItemChecked(index, true);
    			
    			
    			DetailsFragment details = (DetailsFragment)getFragmentManager().findFragmentById(R.id.details);
    			
    			if( details == null || details.getShownIndex() != index){
    				
    				details = DetailsFragment.newInstance(index);
    				
    				android.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
    				//if( index == 0){
    					ft.replace(R.id.details, details);
    				//}else{
    				//	ft.replace(R.id.a_item, details);
    				//}
    				ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
    				ft.commit();
    				
    			}
    		//}
    	}
    	
    }   
}