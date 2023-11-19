package com.example.notesapp.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.example.notesapp.R
import com.example.notesapp.model.Note
import kotlin.random.Random

class NotesAdapter (private val context : Context, val listener: NotesitemclickListener) : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>(){

    private val NoteList = ArrayList<Note>()
    private val fullList = ArrayList<Note>()

    inner class NoteViewHolder(itemview : View) : RecyclerView.ViewHolder(itemview){

        val notes_layout = itemview.findViewById<CardView>(R.id.cv_item)
        val title = itemview.findViewById<TextView>(R.id.tv_title)
        val note_tv = itemview.findViewById<TextView>(R.id.tv_note)
        val date = itemview.findViewById<TextView>(R.id.tv_date)
    }

    interface NotesitemclickListener {
        fun onItemClick(note : Note)
        fun onLongItemClick(note : Note, cardView: CardView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            LayoutInflater.from(context).inflate(R.layout.list_item_note, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return NoteList.size
    }

    fun upDateList(newList : List<Note>){
        fullList.clear()
        fullList.addAll(newList)

        NoteList.clear()
        NoteList.addAll(fullList)
        notifyDataSetChanged()
    }

    fun filterList(search : String){
        NoteList.clear()
        for (item in fullList){
            if (item.title?.lowercase()?.contains(search.lowercase()) == true ||
                item.note?.lowercase()?.contains(search.lowercase()) == true) {
                NoteList.add(item)
            }
        }

        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = NoteList[position]
        holder.title.text = currentNote.title
        holder.title.isSelected = true

        holder.note_tv.text = currentNote.note
        holder.date.text = currentNote.date
        holder.date.isSelected = true

        holder.notes_layout.setCardBackgroundColor(holder.itemView.resources.getColor(randomColor(), null))


        holder.notes_layout.setOnClickListener {
            listener.onItemClick(NoteList[holder.adapterPosition])
        }

        holder.notes_layout.setOnLongClickListener {
            listener.onLongItemClick(NoteList[holder.adapterPosition],holder.notes_layout)
            true
        }

    }

    fun randomColor() : Int{
        val list = ArrayList<Int>()
        list.add(R.color.random1)
        list.add(R.color.random2)
        list.add(R.color.random3)
        list.add(R.color.random4)
        list.add(R.color.random5)
        list.add(R.color.random6)

        val seed = System.currentTimeMillis().toInt()
        val randomIndex = Random(seed).nextInt(list.size)
        return list[randomIndex]
    }
}