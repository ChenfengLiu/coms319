<?php
    include("config.php");
    session_start();
    $error="";
    if($_SERVER["REQUEST_METHOD"] == "POST") {
      // username and password sent from form

      $myusername = mysqli_real_escape_string($db,$_POST['username']);
      $mypassword = mysqli_real_escape_string($db,$_POST['passwd']);
      debug_to_console($myusername);
      debug_to_console($mypassword);

      $sql = "SELECT Password FROM users WHERE UserName = '$myusername'";
      $result = $db->query($sql);
      if ($result->num_rows > 0) {
        if($row = $result->fetch_assoc()) {
            if($mypassword == $row['Password']){
                $_SESSION["UserName"] = $myusername;
                $_SESSION["isLib"] = $row['isLib'];
                header("Location:app.php");
            } else{
                $error = "WRONG PASSWORD!";
            }
        }
    } else {
        $error = "NO MATCH USER";
    }

    $db->close();
   }

   function debug_to_console( $data ) {
       if ( is_array( $data ) )
           $output = "<script>console.log( 'Debug Objects: " . implode( ',', $data) . "' );</script>";
       else
           $output = "<script>console.log( 'Debug Objects: " . $data . "' );</script>";
       echo $output;
   }
?>
<html>

   <head>
      <title>Please Login!</title>
      <link href="loginStyle.css" type="text/css" rel="stylesheet"/>
   </head>

   <body bgcolor = "#FFFFFF">

      <div align = "center">
         <div style = "width:300px; border: solid 1px #333333; " align = "left">
            <div style = "background-color:#333333; color:#FFFFFF; padding:3px;"><b>Login</b></div>

            <div style = "margin:30px">

               <form action = "" method = "post">
                  <label>UserName  :</label><input type = "text" name = "username" class = "box"/><br /><br />
                  <label>Password  :</label><input type = "password" name = "passwd" class = "box" /><br/><br />
                  <input type = "submit" value = " Submit "/><br />
               </form>

               <div style = "font-size:11px; color:#cc0000; margin-top:10px"><?php echo $error; ?></div>

            </div>

         </div>

      </div>

   </body>
</html>
