class Note {
  //use underscore with variables to make them private to our own app.
  //setter not created for id as it will be autogenerated in the table
  int _id;
  String _title;
  String _description;
  String _date;
  int _priority;

  //making description as optional so keep it in inside[]
  Note(this._title, this._date, this._priority, [this._description]);

//named constructor
  Note.withId(this._id, this._title, this._date, this._priority,
      [this._description]);

  int get id => _id;

  String get title => _title;

  int get priority => _priority;

  String get description => _description;

  String get date => _date;

  set title(String newTitle) {
    this._title = newTitle;
    /*if (newTitle.length <= 255) {
      this._title = newTitle;
    }*/
  }

  set description(String newdesc) {
    this._description = newdesc;
    /*if (newdesc.length <= 255) {
      this._description = newdesc;
    }*/
  }

  set priority(int newPriority) {
    if (newPriority >= 1 && newPriority <= 2) {
      this._priority = newPriority;
    }
  }

  set date(String newDate) {
    this._date = newDate;
  }

  //Convert a note object into a Map object as SQFlite deals with only Map Objects
  //use dynamic as value coz we have id as int and descro and title as String
  //dyncamic works for both in runtime.
  Map<String, dynamic> toMap() {
    var map = Map<String, dynamic>();
    if (id != null) {
      map['id'] = _id;
    }

    map['title'] = _title;
    map['description'] = _description;
    map['priority'] = _priority;
    map['date'] = _date;
    return map;
  }

  //Extract NoteObject from Map Object
  //using named constructor here.
  Note.fromMapObject(Map<String, dynamic> map) {
    this._id = map['id'];
    this._title = map['title'];
    this.description = map['description'];
    this._priority = map['priority'];
    this._date = map['date'];
  }
}
