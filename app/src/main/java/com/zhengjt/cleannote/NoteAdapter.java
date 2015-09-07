package com.zhengjt.cleannote;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class NoteAdapter extends BaseAdapter {

  Context mContext;
  List<Note> notes;

  public NoteAdapter(Context context, List<Note> notes) {
    mContext = context;
    this.notes = notes;
  }

  @Override
  public int getCount() {
    return notes != null ? notes.size() : 0;
  }

  @Override
  public Object getItem(int position) {
    if (notes != null)
      return notes.get(position);
    else
      return null;
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
	ViewHolder holder;
	
    if (convertView == null) {
      convertView = LayoutInflater.from(mContext).inflate(R.layout.note_row, null);
      holder = new ViewHolder();
      holder.note = (TextView)convertView.findViewById(R.id.text);
      convertView.setTag(holder);
    }
    else
    {
    	holder = (ViewHolder)convertView.getTag();
    }
    
    Note note = notes.get(position);
    if (note != null)
    	holder.note.setText(note.getTitle());
    return convertView;
  }
  
  static class ViewHolder
  {
	  TextView note;
  }
  

}
