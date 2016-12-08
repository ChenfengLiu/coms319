<?php
    session_start();
?>
    <html>

    <head>
        <title>Library</title>
        <link href="appStyle.css" type="text/css" rel="stylesheet"/>
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
        <script type="text/javascript">
            admin = "<?php echo $_SESSION['isLib']; ?>";
            myName = "<?php echo $_SESSION['UserName']; ?>";
        </script>
        <script src="util.js"></script>
    </head>

    <body>
        <p><a href="logout.php">
        <?php
            echo "<p align='left'>" . $_SESSION['UserName'] . $_SESSION['isLib'] . "</p>";
        ?></a></p>

        <div id="lib">Library</div>
        <br><br>
        <input hidden="true" id="borrow" type="button" value="Borrow" />
        <input hidden="true" id="return" type="button" value="Return" />
        <br><br>
        <div id="description"></div>
        <br><br>
        <div hidden="true" id="form" align="center">
            <label for="bookName">Book Name</label>
            <input id="bookName" type="text" name="bookName" />
            <label for="bookID">Book ID</label>
            <input id="bookID" type="text" name="bookID" />
            <input id="add" type="button" value="Add" />
        </div>
    </body>

    </html>
