package lol.wepekchek.istd.sutdbookingrooms.Authentication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lol.wepekchek.istd.sutdbookingrooms.R;

import static lol.wepekchek.istd.sutdbookingrooms.R.id.booking;

/**
 * Created by 1001827 on 13/12/16.
 */

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private Activity context;
    private Map<String, ArrayList<String>> sharedUsersCollections;
    private ArrayList<String> usersWithAccess;
    private AuthenticationFragment af;

    public ExpandableListAdapter(AuthenticationFragment af, Activity context, ArrayList<String> usersWithAccess, Map<String, ArrayList<String>> sharedUsersCollections){
        this.context = context;
        this.sharedUsersCollections = sharedUsersCollections;
        this.usersWithAccess = usersWithAccess;
        this.af = af;
    }

    public Object getChild(int groupPosition, int childPosition){
        return sharedUsersCollections.get(usersWithAccess.get(groupPosition)).get(childPosition);
    }

    public long getChildId(int groupPosition, int childPosition){
        return childPosition;
    }

    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent){
        final String user = (String) getChild(groupPosition,childPosition);
        LayoutInflater inflater = context.getLayoutInflater();

        if (convertView == null){
            convertView = inflater.inflate(R.layout.child_item, null);
        }

        TextView item = (TextView) convertView.findViewById(R.id.sharedUserItem);
        ImageView delete = (ImageView) convertView.findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Do you want to remove this user?");
                builder.setCancelable(true);
                builder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ArrayList<String> child = sharedUsersCollections.get(usersWithAccess.get(groupPosition));
                                af.removeBooking(child.get(childPosition));
                                child.remove(childPosition);
                                notifyDataSetChanged();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        item.setText(user);
        return convertView;
    }

    public int getChildrenCount(int groupPosition){
        return sharedUsersCollections.get(usersWithAccess.get(groupPosition)).size();
    }

    public Object getGroup(int groupPosition){
        return usersWithAccess.get(groupPosition);
    }

    public int getGroupCount(){
        return usersWithAccess.size();
    }

    public long getGroupId(int groupPosition){
        return groupPosition;
    }

    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent){
        String usersTitle = "Users with access";
        if (convertView==null){
            LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.group_item,null);
        }
        TextView item = (TextView) convertView.findViewById(R.id.listTitle);
        item.setTypeface(null, Typeface.BOLD);
        item.setText(usersTitle);
        return convertView;
    }

    public boolean hasStableIds(){
        return true;
    }

    public boolean isChildSelectable(int groupPosition, int childPosition){
        return true;
    }
}
