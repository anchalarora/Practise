import 'dart:async';
import 'dart:io';

import 'package:notekeeperdbapp/models/note.dart';
import 'package:path_provider/path_provider.dart';
import 'package:sqflite/sqflite.dart';

class DatabaseHelper {
  //only one instance of database helper in whole app.
  static DatabaseHelper _databseHelper;

  static Database _database;

  String noteTable = 'note_table';
  String colId = 'id';
  String colTitle = 'title';
  String colDescription = 'description';
  String colPriority = 'priority';
  String colDate = 'date';

  DatabaseHelper._createInstance(); //Named Constructor to create instance of DatabaseHelper

  factory DatabaseHelper() {
    //calling named Construtor
    if (_databseHelper == null) {
      _databseHelper = DatabaseHelper
          ._createInstance(); //this is executed only once ,singleton object
    }

    return _databseHelper;
  }

  //getter for static var _databseHelper
  Future<Database> get getdatabase async {
    if (_database == null) {
      _database = await initializeDatabase();
    }
    return _database;
  }

  Future<Database> initializeDatabase() async {
    // GEt the directory path for Android and ios to store Databse.
    //getApplicationDocumentsDirectory belongs ot path prvider pkg and returns futire obj so use await keyword
    Directory directory = await getApplicationDocumentsDirectory();

    //define the path to our db
    String path = directory.path + 'notes.db';

    //open /create the db at a given path
    var noteDb = await openDatabase(path, version: 1, onCreate: _createDB);
    return noteDb;
  }

  void _createDB(Database db, int newVersion) async {
    await db.execute('CREATE TABLE $noteTable('
        '$colId INTEGER PRIMARY KEY AUTOINCREMENT, '
        '$colTitle TEXT, '
        '$colDescription TEXT, '
        '$colPriority INTEGER, '
        '$colDate TEXT)');
  }

  //Fetch
  Future<List<Map<String, dynamic>>> getNoteMapList() async {
    var db = await this.getdatabase;
    //var result = await db.rawQuery('SELECT * from $noteTable order by $colPriority ASC');
    //or
    var result = await db.query(noteTable, orderBy: '$colPriority ASC');
    return result;
  }

  //insert
  Future<int> insertNote(Note note) async {
    var db = await this.getdatabase;
    var result = await db.insert(noteTable, note.toMap());
    return result;
  }

  //update
  Future<int> updateNote(Note note) async {
    var db = await this.getdatabase;
    var result = await db.update(noteTable, note.toMap(),
        where: '$colId = ?', whereArgs: [note.id]);
    return result;
  }

  Future<int> deleteNote(int id) async {
    var db = await this.getdatabase;

    int result =
        await db.rawDelete('DELETE FROM $noteTable WHERE $colId = $id');
    return result;
  }

  //get no of records in table
  Future<int> getCount() async {
    var db = await this.getdatabase;
    List<Map<String, dynamic>> all =
        await db.rawQuery('SELECT COUNT (*) fron $noteTable');
    int result = Sqflite.firstIntValue(all);
    return result;
  }

  // get the map list and convert it to note list
  Future<List<Note>> getNoteList() async {
    var noteMapList = await getNoteMapList();
    int count = noteMapList.length;

    List<Note> noteList = List<Note>();
    //for loop to creste notlist from maplist
    for (int i = 0; i < count; i++) {
      noteList.add(Note.fromMapObject(noteMapList[i]));
    }
    return noteList;
  }
}
