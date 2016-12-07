// CALCULATOR.JS
//
//
//
var exp1 = "", exp2 = "";
var op = "", oldOP = "";
var isExp1 = true, isValidExp = false;

var btPressed = "";
var mBtPressed = "";
var mVal = "0";
var isRefreshed = true;
// 
var Calc = {

	Model : {
	},


	View : {
		textRow : {id: "textRow", type: "text", value: "0", onclick:""},
		button0 : {id: "button0", type: "button", value: 0, onclick:""},
		button1 : {id: "button1", type: "button", value: 1, onclick:""},
		button2 : {id: "button2", type: "button", value: 2, onclick:""},
		button3 : {id: "button3", type: "button", value: 3, onclick:""},
		button4 : {id: "button4", type: "button", value: 4, onclick:""},
		button5 : {id: "button5", type: "button", value: 5, onclick:""},
		button6 : {id: "button6", type: "button", value: 6, onclick:""},
		button7 : {id: "button7", type: "button", value: 7, onclick:""},
		button8 : {id: "button8", type: "button", value: 8, onclick:""},
		button9 : {id: "button9", type: "button", value: 9, onclick:""},
		
		buttonP : {id: "buttonP", type: "button", value: "+", onclick:""},
		buttonS : {id: "buttonS", type: "button", value: "-", onclick:""},
		buttonT : {id: "buttonT", type: "button", value: "*", onclick:""},
		buttonD : {id: "buttonD", type: "button", value: "/", onclick:""},
		buttonO : {id: "buttonO", type: "button", value: ".", onclick:""},
		buttonE : {id: "buttonE", type: "button", value: "=", onclick:""},
		buttonC : {id: "buttonC", type: "button", value: "C", onclick:""},
		
		buttonMR : {id: "buttonMR", type: "button", value: "MR", onclick:""},
		buttonMS : {id: "buttonMS", type: "button", value: "M-", onclick:""},
		buttonMP : {id: "buttonMP", type: "button", value: "M+", onclick:""},
		buttonMC : {id: "buttonMC", type: "button", value: "MC", onclick:""}
	},

	Controller : {
		expHelper : function(that) {
			var num = that.value;
			if(isExp1 && isValidExp){
				exp1 = exp1 + num + "";
				exp2 = "";
				isValidExp = false;
				operator = "";
				Calc.View["textRow"].value = exp1;
				document.getElementById("textRow").value = exp1;
			}else if(!isExp1 && isValidExp){
				exp2 = exp2 + num + "";
				Calc.View["textRow"].value = exp2;
				document.getElementById("textRow").value = exp2;
			}else if(isExp1 && !isValidExp){
				if(!isRefreshed){
					console.log("needs refresh.");
					exp1 = num + "";
					console.log("exp1: " + exp1 + ".");
					Calc.View["textRow"].value = exp1;
					document.getElementById("textRow").value = exp1;
					isRefreshed = true;
				}else{
					console.log("should display this.");
					exp1 = exp1 + num + "";
					console.log("exp1: " + exp1 + ".");
					Calc.View["textRow"].value = exp1;
					document.getElementById("textRow").value = exp1;
				}
				
			}else if(!isExp1 && !isValidExp){
				if(op == ""){
					console.log("hahahaha: op == ");
					isExp1 = false;
					exp1 = exp1 + num + "";
					Calc.View["textRow"].value = exp1;
					document.getElementById("textRow").value = exp1;
				}else{
					console.log("heiheihei: op !=");
					console.log("op is: " + op);
					exp2 = exp2 + num + "";
					isValidExp = true;
					Calc.View["textRow"].value = exp2;
					document.getElementById("textRow").value = exp2;
				}
			}
		},
		opHelper : function(that) {
			var thatOP = that.value;
			if(isExp1 && !isValidExp){
				op = thatOP;
				isExp1 = false;
			}else if(!isExp1 && isValidExp){
				console.log("oldOP: " + oldOP + ".");
				console.log("op: " + op + ".");
				oldOP = op;
				op = thatOP;
				console.log("oldOP: " + oldOP + ".");
				console.log("op: " + op + ".");
				btPressed = oldOP;
				Calc.Controller.evaluate(oldOP);
			}else if(!isExp1 && !isValidExp){
				if(op == ""){
					op = thatOP;
				}else{
					Calc.View["textRow"].value = exp2;
					document.getElementById("textRow").value = exp2;
				}
			}
		},
		evHelper : function(a, b, m){
			var doubleA = parseFloat(a);
			var doubleB = parseFloat(b);
			if(m == "+"){
				return (doubleA + doubleB) + "";
			}else if(m == "-"){
				return (doubleA - doubleB) + "";
			}else if(m == "*"){
				return (doubleA * doubleB) + "";
			}else if(m == "/"){
				if(doubleB == 0){
					return "error:div0";
				}else{
					return (doubleA / doubleB) + "";
				}
			}else{
				return doubleA + "";
			}

		},
		evaluate : function(buttonPressed){
			btPressed = buttonPressed.value;
			console.log("buttonPressed: " + btPressed);
			if(btPressed == "="){
				if(isValidExp){
					var a = parseFloat(exp1);
					console.log("the exp1 is: " + exp1);
					var b = parseFloat(exp2);
					console.log("the exp2 is: " + exp2);
					var answer = Calc.Controller.evHelper(a, b, op);
					console.log("the answer is: " + answer);
					if(answer == "error:div0"){
						Calc.View["textRow"].value = answer;
						document.getElementById("textRow").value = answer;
					}else{
						exp1 = answer + "";
						console.log("evaluate: exp1: " + exp1 + ".");
						exp2 = "";
						op = "";
						oldOP = "";
						isExp1 = false;
						isValidExp = false;
						Calc.View["textRow"].value = exp1;
						document.getElementById("textRow").value = exp1;
					}
				}else{
					if(isExp1){
						exp1 = "";
					}
				}//End inner if-else
			}else if((btPressed == "+")||(btPressed == "-")||(btPressed == "*")||(btPressed == "/")){
				if(isValidExp){
					var a = parseFloat(exp1);
					var b = parseFloat(exp2);
					var answer = Calc.Controller.evHelper(a, b, btPressed);
					if(answer == "error:div0"){
						Calc.View["textRow"].value = answer;
						document.getElementById("textRow").value = answer;
					}else{
						exp1 = answer + "";
						exp2 = "";
						isExp1 = false;
						isValidExp = false;
						Calc.View["textRow"].value = exp1;
						document.getElementById("textRow").value = exp1;
					}
				}//End inner if
			}//End if, else-if
		},
		clear : function(){
			exp1 = "";
			exp2 = "";
			op = "";
			oldOP = "";
			isValidExp = false;
			isEx1 = true;
			Calc.View["textRow"].value = "0";
			document.getElementById("textRow").value = "0";
		},

		mHelper : function(mButton){
			mBtPressed = mButton.value;
			if(mBtPressed == "MR"){
				document.getElementById("textRow").value = mVal;
				console.log("mVal is: " + mVal);
				isRefreshed = false;
			}else if(mBtPressed == "M+"){
				var num = document.getElementById("textRow").value;
				mVal = parseFloat(mVal) + parseFloat(num);
				isRefreshed = false;
			}else if(mBtPressed == "M-"){
				var num = document.getElementById("textRow").value;
				mVal = parseFloat(mVal) - parseFloat(num);
				isRefreshed = false;
			}else if(mBtPressed == "MC"){
				mVal = "0";
			}
		}
	},

	run : function() {
		Calc.attachHandlers();
		console.log(Calc.display());
		return Calc.display();
	},


	displayElement : function (element) {
		var s = "<input ";
		s += " id=\"" + element.id + "\"";
		s += " type=\"" + element.type + "\"";
		s += " value= \"" + element.value + "\"";
		s += " onclick= \"" + element.onclick + "\"";
		s += ">";
		return s;

	},

	display : function() {
		var s;
		s = "<table id=\"myTable\" border=2>"
		s += "<tr><td>" + Calc.displayElement(Calc.View.textRow) + "</td></tr>";
		s += "<tr><td>";
		s += Calc.displayElement(Calc.View.button7);
		s += Calc.displayElement(Calc.View.button8);
		s += Calc.displayElement(Calc.View.button9);
		s += Calc.displayElement(Calc.View.buttonP);
		s += "<tr><td>";
		s += Calc.displayElement(Calc.View.button4);
		s += Calc.displayElement(Calc.View.button5);
		s += Calc.displayElement(Calc.View.button6);
		s += Calc.displayElement(Calc.View.buttonS);
		s += "<tr><td>";
		s += Calc.displayElement(Calc.View.button1);
		s += Calc.displayElement(Calc.View.button2);
		s += Calc.displayElement(Calc.View.button3);
		s += Calc.displayElement(Calc.View.buttonT);
		s += "<tr><td>";
		s += Calc.displayElement(Calc.View.button0);
		s += Calc.displayElement(Calc.View.buttonO);
		s += Calc.displayElement(Calc.View.buttonE);
		s += Calc.displayElement(Calc.View.buttonD);
		s += "<tr><td>";
		s += Calc.displayElement(Calc.View.buttonC);
		s += Calc.displayElement(Calc.View.buttonMR);
		s += Calc.displayElement(Calc.View.buttonMS);
		s += Calc.displayElement(Calc.View.buttonMP);
		s += "<tr><td>";
		s += Calc.displayElement(Calc.View.buttonMC);
		s += "</tr></td></table>";
		return s;
	},

	attachHandlers : function() {
		for(var i = 0; i <=9; i++){
			Calc.View["button"+i].onclick = "Calc.Controller.expHelper(this)";
		}
		Calc.View["buttonO"].onclick = "Calc.Controller.expHelper(this)";

		Calc.View["buttonP"].onclick = "Calc.Controller.opHelper(this)";
		Calc.View["buttonS"].onclick = "Calc.Controller.opHelper(this)";
		Calc.View["buttonT"].onclick = "Calc.Controller.opHelper(this)";
		Calc.View["buttonD"].onclick = "Calc.Controller.opHelper(this)";

		Calc.View["buttonE"].onclick = "Calc.Controller.evaluate(this)";
		Calc.View["buttonC"].onclick = "Calc.Controller.clear()";

		Calc.View["buttonMR"].onclick = "Calc.Controller.mHelper(this)";
		Calc.View["buttonMS"].onclick = "Calc.Controller.mHelper(this)";
		Calc.View["buttonMP"].onclick = "Calc.Controller.mHelper(this)";
		Calc.View["buttonMC"].onclick = "Calc.Controller.mHelper(this)";
	},



} // end of Calc;

