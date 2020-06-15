package com.example.inventorysystem.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inventorysystem.Note;
import com.example.inventorysystem.R;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter  extends RecyclerView.Adapter<NoteAdapter.NoteHolder> {


        private List<Note> notes = new ArrayList<>();
        private NoteAdapter.OnItemClickListener listener;

        @NonNull
        @Override
        public NoteAdapter.NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View noteView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.note_item, parent, false);
            return new NoteHolder(noteView);
        }

        @Override
        public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
            Note currentNote = notes.get(position);
            holder.textViewNote.setText(currentNote.getNote());

        }

        @Override
        public int getItemCount() {
            return notes.size();
        }

        public void setNotes(List<Note> notes){
            this.notes = notes;
            notifyDataSetChanged();
        }

        public Note getNoteAt(int position){
            return notes.get(position);
        }

        class NoteHolder extends RecyclerView.ViewHolder{
            private TextView textViewNote;

            public NoteHolder(@NonNull View itemView) {
                super(itemView);
                textViewNote = itemView.findViewById(R.id.text_view_note);


                itemView.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        int position = getAdapterPosition();
                        if (listener != null && position != RecyclerView.NO_POSITION){
                            listener.onItemClick(notes.get(position));
                        }

                    }

                });
            }
        }

        public interface OnItemClickListener {
            void onItemClick(Note note);
        }

        public void setOnItemClickListener(NoteAdapter.OnItemClickListener listener){
            this.listener = listener;
        }

    }
