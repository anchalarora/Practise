import 'package:flutter/material.dart';
import 'package:notekeeperdbapp/screens/note_list.dart';


void main() => runApp(MyApp());

class MyApp extends StatelessWidget{
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: "NoteKeeper",
      debugShowCheckedModeBanner: false,
      theme: ThemeData(
        primarySwatch: Colors.deepPurple
      ),
      home : NoteList(),
    );
  }

}

/*
import 'dart:async';

void main(){
  printFileContent();

}

printFileContent1() async{
  Future<String> fileContent =  downloadFile();
  fileContent.then((resultString){
    print('The content of the file is  $resultString');
  });

}
printFileContent() async{
  String fileContent = await downloadFile();
  print('The content of the file is  $fileContent');
}

Future<String> downloadFile(){
  Future<String> result = Future.delayed(Duration(seconds:6),(){
    return 'My secret file content';
  });
  return result;
}
*/
