$(document).ready(function() {
    localStorage.clear();
    myLib = new Library();

});

function Library() {
    this.shelfs = new Array(4);
    var shelfs = this.shelfs;

    //Get books from database
    $.post("connectDb.php", {
        postMethod: "s"
    }, function(data, status) {
        var mJson = JSON.parse(data);
        shelfs[0] = new Shelf("Art", 0, mJson[0]);
        shelfs[1] = new Shelf("Science", 1, mJson[1]);
        shelfs[2] = new Shelf("Sport", 2, mJson[2]);
        shelfs[3] = new Shelf("Literature", 3, mJson[3]);

        Library.prototype.create();
        status = true;
    });

};

Library.prototype.create = function() {
    var table = $("<table id='table' border='2'></table>"); // creates DOM elements
    var tbody = $('<tbody></tbody>');
    var row = $('<tr></tr>');
    for (var i = 0; i < 4; i++) {
        var cell = $('<th width="80" height="18"></th>');
        cell.append(myLib.shelfs[i].name);
        row.append(cell);
        tbody.append(row);
    }


    for (var j = 0; j < 20; j++) {
        row = $('<tr></tr>');
        for (i = 0; i < 4; i++) {
            var cell = $('<td class="currentB" width="80" height="18"></td>');
            if (myLib.shelfs[i].books[j] != undefined) {
                cell.append(myLib.shelfs[i].books[j].bookName);
            }
            row.append(cell);
        }
        tbody.append(row);
    }
    table.append(tbody);
    table.insertAfter($('#lib'));
    myLib.style();

    var currentB = null;
    $("#table td").click(function() {
        $("#borrow").hide();
        $("#return").hide();
        var r = $(this).parent().index('tr');
        var c = this.cellIndex;
        var value = $(this).html();

        if (value != "") {
            currentB = myLib.shelfs[c].books[r - 1];
            $("#description").html("Book name: " + currentB.bookName + " ID: " + currentB.bookID + " Borrowed By: " + currentB.borrow + " Availability: " + currentB.isA);
            if (admin == 0) {
                $("#borrow").show();
                if (document.cookie.substring(9) == currentB.borrow) {
                    $("#return").show();
                }
            }
        }

    });

    $("#borrow").click(function() {
        var username = document.cookie.substring(9);
        var userStr = localStorage.getItem(username);
        var result = "Borrow Failed!";
        var user;
        if (userStr == undefined) {
            user = new User(username);
        } else {
            user = JSON.parse(userStr);
        }
        if (user.numBooks < 2 && currentB.isA == 1) {
            user.numBooks += 1;
            currentB.borrow = user.name;
            currentB.isA = 0;
            result = "Borrow Success!";
        }

        var usrStr = JSON.stringify(user);
        localStorage.setItem(user.name, usrStr);
        var shelfStr = JSON.stringify(myLib.shelfs);
        localStorage.setItem("shelfs", shelfStr);

        $("#borrow").hide();
        $("#description").html(result);

        myLib.style();

    });

    $("#return").click(function() {
        $("#return").hide();
        var username = document.cookie.substring(9);
        var userString = localStorage.getItem(username);
        var user = JSON.parse(userString);
        if (user.numBooks > 0)
            user.numBooks -= 1;
        currentB.borrow = null;
        currentB.isA = 1;

        userString = JSON.stringify(user);
        localStorage.setItem(user.name, userString);

        var shelfString = JSON.stringify(myLib.shelfs);
        localStorage.setItem("shelfs", shelfString);

        $("#description").html("Return Success!");
        myLib.style();
    });

};

Library.prototype.style = function() {
    for (var i = 0; i < 4; i++) {
        for (var j = 0; j < this.shelfs[i].books.length; j++) {
            var r = $("#table tbody")[0].children[j + 1];
            var c = $(r)[0].children[i];
            if (this.shelfs[i].books[j].isA == 1) {
                $(c).css("background-color", "#ffffff");
            } else {
                $(c).css("background-color", "#ff0000");
            }
        }
    }
}

////////////////////////////////////////////////////////////////////////////////
//objects: user, shelf, books
////////////////////////////////////////////////////////////////////////////////

function User(name) {
    this.name = name;
    this.numBooks = 0;
};

function Shelf(name, id, books) {
    this.id = id;
    this.name = name;
    this.books = books;
};

function Book(name, id, author, isA) {
    this.bookName = name;
    this.bookID = id;
    this.author = author;
    this.isA = isA;
};
