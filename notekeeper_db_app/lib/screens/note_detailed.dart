import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:notekeeperdbapp/models/note.dart';
import 'package:notekeeperdbapp/utils/DatabaseHelper.dart';

class NoteDetail extends StatefulWidget {
  final String appBarTitle;
  final Note note;

  NoteDetail(this.note, this.appBarTitle);

  @override
  State<StatefulWidget> createState() {
    return NoteDetailState(this.note, this.appBarTitle);
  }
}

class NoteDetailState extends State<NoteDetail> {
  DatabaseHelper databaseHelper = DatabaseHelper();

  String appBarTitle;
  Note note;

  NoteDetailState(this.note, this.appBarTitle);

  static var _priorities = ['High', 'Low'];

  TextEditingController titleController = TextEditingController();
  TextEditingController descController = TextEditingController();

  @override
  Widget build(BuildContext context) {
    var textStyle = Theme
        .of(context)
        .textTheme
        .title;
    titleController.text = note.title;
    descController.text = note.description;

    return WillPopScope(
        onWillPop: () {
          moveToLastScreen();
        },
        child: Scaffold(
            appBar: AppBar(
              title: Text(appBarTitle),
              leading: IconButton(
                icon: Icon(Icons.arrow_back),
                onPressed: () {
                  moveToLastScreen();
                },
              ),
            ),
            body: Padding(
                padding: EdgeInsets.only(top: 15.0, left: 10.0, right: 10.0),
                child: ListView(
                  children: <Widget>[
                    ListTile(
                        title: DropdownButton(
                            items: _priorities.map((String dropDownStringItem) {
                              return DropdownMenuItem<String>(
                                value: dropDownStringItem,
                                child: Text(dropDownStringItem),
                              );
                            }).toList(),
                            style: textStyle,
                            value: getPriorityAsString(note.priority),
                            onChanged: (newValueSelected) {
                              setState(() {
                                debugPrint('User Selected $newValueSelected');
                                updatePriorityAsInt(newValueSelected);
                              });
                            })),
                    //Second Element
                    Padding(
                        padding: EdgeInsets.only(top: 15.0, bottom: 15.0),
                        child: TextField(
                          controller: titleController,
                          style: textStyle,
                          onChanged: (value) {
                            debugPrint("Some value changed");
                            updateTitle();
                          },
                          decoration: InputDecoration(
                              labelText: 'Title',
                              labelStyle: textStyle,
                              border: OutlineInputBorder(
                                  borderRadius: BorderRadius.circular(5.0))),
                        )),
                    Padding(
                        padding: EdgeInsets.only(top: 15.0, bottom: 15.0),
                        child: TextField(
                          controller: descController,
                          style: textStyle,
                          onChanged: (value) {
                            debugPrint("Some value changed");
                            updateDecsription();
                          },
                          decoration: InputDecoration(
                              labelText: 'Description',
                              labelStyle: textStyle,
                              border: OutlineInputBorder(
                                  borderRadius: BorderRadius.circular(5.0))),
                        )),
                    Padding(
                        padding: EdgeInsets.only(top: 15.0, bottom: 15.0),
                        child: Row(
                          children: <Widget>[
                            Expanded(
                                child: RaisedButton(
                                  color: Theme
                                      .of(context)
                                      .primaryColorDark,
                                  textColor: Theme
                                      .of(context)
                                      .primaryColorLight,
                                  child: Text(
                                    "Save",
                                    textScaleFactor: 1.5,
                                  ),
                                  onPressed: () {
                                    setState(() {
                                      _saveNoteToDB();
                                      debugPrint("Save Button Clicked");
                                    });
                                  },
                                )),
                            Container(width: 5.0),
                            Expanded(
                                child: RaisedButton(
                                  color: Theme
                                      .of(context)
                                      .primaryColorDark,
                                  textColor: Theme
                                      .of(context)
                                      .primaryColorLight,
                                  child: Text(
                                    "Delete",
                                    textScaleFactor: 1.5,
                                  ),
                                  onPressed: () {
                                    setState(() {
                                      _deleteNote();
                                      debugPrint("Delete Button Clicked");
                                    });
                                  },
                                ))
                          ],
                        ))
                  ],
                ))));
  }

  void moveToLastScreen() {
    Navigator.pop(context, true);
  }

  //convert the string priority in the form of integer before saving it to database
  void updatePriorityAsInt(String value) {
    switch (value) {
      case 'High':
        note.priority = 1;
        break;
      case 'Low':
        note.priority = 2;
        break;
    }
  }

  //convert the int priority to String priority and display to user in dropdwon
  String getPriorityAsString(int value) {
    String priority;
    switch (value) {
      case 1:
        priority = _priorities[0]; //High
        break;
      case 2:
        priority = _priorities[1]; //Low
        break;
    }

    return priority;
  }

  void updateTitle() {
    note.title = titleController.text;
  }

  void updateDecsription() {
    note.description = descController.text;
  }

  void _saveNoteToDB() async {
    moveToLastScreen();

    note.date = DateFormat.yMMMd().format(DateTime.now());
    int result;
    if (note.id != null) {
      //perform update operation

      result = await databaseHelper.updateNote(note);
    } else {
      result = await databaseHelper.insertNote(note);
      //perform insert operation
    }

    if (result != 0) {
      //success
      _showAlertDialog("Status", "Note Saved Successfully");
    } else {
      //failure
      _showAlertDialog("Status", "Problem Saving Note");
    }
  }

  void _deleteNote() async {
    moveToLastScreen();
    //case 1 when new note has to be deleted
    if (note.id == null) {
      _showAlertDialog("Status", "Note note Deleted");
      return;
    }

    int result = await databaseHelper.deleteNote(note.id);
    if (result != 0) {
      //success
      _showAlertDialog("Status", "Note Deleted Successfully");
    } else {
      //failure
      _showAlertDialog("Status", "Problem Deleting Note");
    }
  }

  void _showAlertDialog(String title, String message) {
    AlertDialog alertDialog = AlertDialog(
      title: Text(title),
      content: Text(message),
    );
    showDialog(context: context, builder: (_) => alertDialog);
  }
}
