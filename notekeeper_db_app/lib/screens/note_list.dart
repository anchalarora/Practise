import 'package:flutter/material.dart';
import 'package:notekeeperdbapp/screens/note_detailed.dart';
import 'package:notekeeperdbapp/models/note.dart';
import 'package:notekeeperdbapp/utils/DatabaseHelper.dart';
import 'dart:async';
import 'package:sqflite/sqflite.dart';

class NoteList extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return NoteListState();
  }
}

class NoteListState extends State<NoteList> {
  int count = 0;
  DatabaseHelper databaseHelper = DatabaseHelper();
  List<Note> noteList;

  @override
  Widget build(BuildContext context) {
    if (noteList == null) {
      noteList = List<Note>();
      updateListView();
    }
    return Scaffold(
      appBar: AppBar(
        title: Text('Notes'),
      ),
      body: getNoteListView(),
      floatingActionButton: FloatingActionButton(
          onPressed: () {
            debugPrint("FAB Clicked");
            navigateToDetail(Note('','',2),'Add Note');
          },
          tooltip: "Add Note",
          child: Icon(Icons.add)),
    );
  }

  ListView getNoteListView() {
    TextStyle titleStyle = Theme.of(context).textTheme.subhead;

    return ListView.builder(
        itemCount: count,
        itemBuilder: (BuildContext context, int position) {
          return Card(
            color: Colors.white,
            elevation: 2.0,
            child: ListTile(
              leading: CircleAvatar(
                backgroundColor:
                    getPrioorityColor(this.noteList[position].priority),
                child: getPriorityIcon(this.noteList[position].priority),
              ),
              title: Text(
                this.noteList[position].title,
                style: titleStyle,
              ),
              subtitle: Text(this.noteList[position].date),
              trailing: GestureDetector(
                child :Icon(Icons.delete, color: Colors.grey),
                    onTap: (){
                  _delete(context, noteList[position]);

            }
              ),

              onTap: () {
                debugPrint("ListTile Clicked");
                navigateToDetail(this.noteList[position],'Edit Note');
              },
            ),
          );
        });
  }

  //Reutrn the priority color
  Color getPrioorityColor(int priority) {
    switch (priority) {
      case 1:
        return Colors.red;
        break;
      case 2:
        return Colors.yellow;
        break;
      default:
        return Colors.yellow;
    }
  }

  //Return the priority icon

  Icon getPriorityIcon(int priority) {
    switch (priority) {
      case 1:
        return Icon(Icons.play_arrow);
        break;
      case 2:
        return Icon(Icons.keyboard_arrow_right);
        break;
      default:
        return Icon(Icons.keyboard_arrow_right);
    }
  }

  //delete the list note item
  void _delete(BuildContext context, Note note) async {
    int result = await databaseHelper.deleteNote(note.id);
    if (result != 0) {
      _showSnackBar(context, "Note Deleted Successfully");
      updateListView();
    }
  }

  void navigateToDetail(Note note ,String title) async {
    bool result = await Navigator.push(context, MaterialPageRoute(builder: (context) {
      return NoteDetail(note,title);
    }));
    if(result== true){
      updateListView();
    }
  }

  void _showSnackBar(BuildContext context, String message) {
    final snackbar = SnackBar(content: Text(message));
    Scaffold.of(context).showSnackBar(snackbar);
  }

  void updateListView() {
    final Future<Database> dbfuture = databaseHelper.initializeDatabase();
    dbfuture.then((database){

      Future<List<Note>> noteListFuture = databaseHelper.getNoteList();
      noteListFuture.then((noteList){
        setState(() {
          this.noteList = noteList;
          this.count = noteList.length;
        });
      });
    });
  }
}
