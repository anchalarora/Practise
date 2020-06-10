import 'package:flutter/material.dart';

void main() {
  runApp(MaterialApp(
    debugShowCheckedModeBanner: false,
    title: "My Calculator App",
    home: SimpleInterestform(),
    theme: ThemeData(
        brightness: Brightness.dark,
        primaryColor: Colors.indigo,
        accentColor: Colors.indigoAccent),
  ));
}

class SimpleInterestform extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    // TODO: implement createState
    return _SimpleInterestformState();
  }
}

class _SimpleInterestformState extends State<SimpleInterestform> {

  var _formKey = GlobalKey<FormState>();

  var _currencies = ['Rupees', 'Dollars', 'Pounds', 'Others'];
  var _minimum_padding = 5.0;

  //only static members can be initialized here so use initState() to initilaize _currentItemSelected
  var _currentItemSelected = '';
  TextEditingController principalController = TextEditingController();
  TextEditingController roiController = TextEditingController();
  TextEditingController termController = TextEditingController();

  var displayResult = "";

  @override
  void initState() {
    super.initState();
    _currentItemSelected = _currencies[0];
  }

  @override
  Widget build(BuildContext context) {
    TextStyle textStyle = Theme.of(context).textTheme.title;
    return Scaffold(
        //resizeToAvoidBottomPadding: false,
        appBar: AppBar(
          title: Text("Simple Interest Calculator"),
        ),
        body: Form(
          key:_formKey,
          child: Padding(
              padding: EdgeInsets.all(_minimum_padding * 2),
              //margin: EdgeInsets.all(_minimum_padding * 2),
              child: ListView(
                children: <Widget>[
                  getImageAsset(),
                  Padding(
                      padding: EdgeInsets.only(
                          top: _minimum_padding, bottom: _minimum_padding),
                      child: TextFormField(
                        keyboardType: TextInputType.number,
                        style: textStyle,
                        controller: principalController,
                        validator: (String value){
                          if(value.isEmpty){
                            return 'Please enter Principal Amount';
                          }
                        },
                        decoration: InputDecoration(
                            labelText: "Principal",
                            hintText: "Enter Principal e.g 12000",
                            errorStyle: TextStyle(
                              color:Colors.yellowAccent,
                              fontSize: 15.0
                            ),
                            labelStyle: textStyle,
                            border: OutlineInputBorder(
                                borderRadius: BorderRadius.circular(5.0))),
                      )),
                  Padding(
                      padding: EdgeInsets.only(
                          top: _minimum_padding, bottom: _minimum_padding),
                      child: TextFormField(
                        keyboardType: TextInputType.number,
                        style: textStyle,
                        controller: roiController,
                          validator:(String value){
                          if(value.isEmpty){
                            return 'Please Enter rate of interest';
                          }
                        },
                        decoration: InputDecoration(
                            labelText: "Rate of Interest",
                            hintText: "In Percent",
                            errorStyle: TextStyle(
                              color:Colors.yellowAccent,
                            ),
                            labelStyle: textStyle,
                            border: OutlineInputBorder(
                                borderRadius: BorderRadius.circular(5.0))),
                      )),
                  Padding(
                      padding: EdgeInsets.only(
                          top: _minimum_padding, bottom: _minimum_padding),
                      child: Row(
                        children: <Widget>[
                          Expanded(
                              child: TextFormField(
                            keyboardType: TextInputType.number,
                            style: textStyle,
                            controller: termController,
                            validator: (String value){
                              if(value.isEmpty){
                                return 'Please Enter term in years';
                              }
                            },
                            decoration: InputDecoration(
                                labelText: "Term",
                                hintText: "Time in years",
                                labelStyle: textStyle,
                                errorStyle: TextStyle(
                                  color :Colors.yellowAccent
                                ),
                                border: OutlineInputBorder(
                                    borderRadius: BorderRadius.circular(5.0))),
                          )),
                          Container(
                            width: _minimum_padding * 5,
                          ),
                          Expanded(
                              child: DropdownButton<String>(
                            items: _currencies.map((String selectedvalue) {
                              return DropdownMenuItem<String>(
                                value: selectedvalue,
                                child: Text(selectedvalue),
                              );
                            }).toList(),
                            value: _currentItemSelected,
                            onChanged: (String newValueSelected) {
                              _dropDownItemSelected(newValueSelected);
                            },
                          ))
                        ],
                      )),
                  Padding(
                      padding: EdgeInsets.only(
                          top: _minimum_padding, bottom: _minimum_padding),
                      child: Row(
                        children: <Widget>[
                          Expanded(
                              child: RaisedButton(
                            color: Theme.of(context).accentColor,
                            textColor: Theme.of(context).primaryColorDark,
                            child: Text(
                              "Calculate",
                              textScaleFactor: 1.5,
                            ),
                            onPressed: () {

                              setState(() {
                                if(_formKey.currentState.validate()) {
                                  this.displayResult = _calculateTotalReturn();
                                }
                              });
                            },
                          )),
                          Expanded(
                              child: RaisedButton(
                            color: Theme.of(context).primaryColorDark,
                            textColor: Theme.of(context).primaryColorLight,
                            child: Text(
                              "Reset",
                              textScaleFactor: 1.5,
                            ),
                            onPressed: () {
                              setState(() {
                                _reset();
                              });
                            },
                          ))
                        ],
                      )),
                  Padding(
                      padding: EdgeInsets.all(_minimum_padding * 2),
                      child: Text(
                        this.displayResult,
                        style: textStyle,
                      ))
                ],
              )),
        ));
  }

  Widget getImageAsset() {
    AssetImage assetImage = AssetImage('images/flight.png');
    Image image = Image(image: assetImage, width: 125.0, height: 125.0);
    return Container(
        child: image, margin: EdgeInsets.all(_minimum_padding * 10));
  }

  void _dropDownItemSelected(String newValueSelected) {
    setState(() {
      _currentItemSelected = newValueSelected;
    });
  }

  String _calculateTotalReturn() {
    double principal = double.parse(principalController.text);
    double roi = double.parse(roiController.text);
    double term = double.parse(termController.text);

    double totatAmoutPayable = principal + (principal * roi * term) / 100;

    String result =
        "After $term years ,your investment will be worth $totatAmoutPayable $_currentItemSelected";

    return result;
  }

  void _reset() {
    principalController.text = '';
    roiController.text = "";
    termController.text = "";
    displayResult = '';
    _currentItemSelected = _currencies[0];
  }
}
