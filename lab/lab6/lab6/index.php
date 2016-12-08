<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title>Please Sign Up!</title>
    <link href="signupStyle.css" type="text/css" rel="stylesheet"/>
  </head>
  <body>
    <?php
    include("config.php");
    //define variables
    $firstNameErr = $lastNameErr = $usernameErr = $emailErr = $phoneErr = "";
    $firstName = $lastName = $username = $email = $phone = $isLib = "";
    $passwd = $rePasswd = $passwdErr = $rePasswdErr = "";
    $check = array(false, false, false, false, false, false, false);


    if($_SERVER["REQUEST_METHOD"] == "POST"){
      //first name
      if(empty($_POST["firstName"])){
        $firstNameErr = "First name is required";
      }else{
        $firstName = test_input($_POST["firstName"]);
        $check[0] = true;
        //check if first name only contains letters
        if (!preg_match("/^[a-zA-Z]*$/",$firstName)) {
          $firstNameErr = "Only letters allowed";
        }
      }

      //last name
      if(empty($_POST["lastName"])){
        $lastNameErr = "Last name is required";
      }else{
        $lastName = test_input($_POST["lastName"]);
        $check[1] = true;
        //check if first name only contains letters
        if (!preg_match("/^[a-zA-Z]*$/",$lastName)) {
          $lastNameErr = "Only letters allowed";
        }
      }

      //username
      if(empty($_POST["username"])){
        $usernameErr = "Username is required";
      }else {
        $username = test_input($_POST["username"]);
        $check[2] = true;
        //check if name only contains letters and whitespace
        if (!preg_match("/^[a-zA-Z ]*$/",$username)) {
          $usernameErr = "Only letters and white space allowed";
        }
      }

      //password
      if(empty($_POST["passwd"])){
        $passwdErr = "Password is required";
      }else{
        $passwd = test_input($_POST["passwd"]);
        $check[3] = true;
        if(!preg_match("/^[a-zA-Z]\w{3,14}$/", $passwd)){
          if(!ctype_alpha($passwd[0])){
            $passwdErr = "The first letter must be aphabetical";
          }else if(!(strlen($passwd)<15 && strlen($passwd)>3)){
            $passwdErr = "Should be at least 4 characters and no more than 15 characters";
          }else{
            $passwdErr = "No special characters allowed";
          }
        }
      }

      //re-enter Password
      if(empty($_POST["rePasswd"])){
        $rePasswdErr = "please re-enter your password";
      }else{
        $rePasswd = test_input($_POST["rePasswd"]);
        $check[4] = true;
        if(strcmp($rePasswd, $passwd) != 0){
          $rePasswdErr = "Password not match";
        }
      }

      //email
      if (empty($_POST["email"])) {
        $emailErr = "Email is required";
      }else{
        $email = test_input($_POST["email"]);
        $check[5] = true;
        //check if email address is well-formed
        if(!filter_var($email, FILTER_VALIDATE_EMAIL)){
          $emailErr = "Invalid email format";
        }
      }

      //phone
      if (empty($_POST["phone"])) {
        $phoneErr = "This is required";
      } else {
        $phone = test_input($_POST["phone"]);
        $check[6] = true;
        //check if phone number is valid
        if(!preg_match("/^([0-9]{3}-[0-9]{3}-[0-9]{4}|[0-9]{10})$/", $phone)){
          $phoneErr = "Invalid phone format ('xxxxxxxxxx' or 'xxx-xxx-xxxx')";
        }
      }

      //isLibrarian
      if(!empty($_POST["isLib"])){
        $isLib = test_input($_POST["isLib"]);
      }

      if(isset($_POST['submit'])){
        if(canSubmit($check)){
          debug_to_console("lets connect to DBDBD");
          //check if connect
          if ($db->connect_error) {
            die("Connection failed: " . $db->connect_error);
            exit();
          }
          //check if user exists
          $sql = "SELECT UserName FROM users WHERE UserName='$username'";
          $result = $db->query($sql);
          //check if user exists
          if ($result->num_rows > 0) {
            debug_to_console("user exists!");
            $db->close();
            exit();
          }
          //otherwise insert user
          if($isLib){$isLib = 1;}
          $sql = "INSERT INTO users VALUES ('$username', '$passwd', '$email', '$phone', '$isLib', '$firstName', '$lastName')";
          if ($db->query($sql) === TRUE) {
            debug_to_console("user inserted!");
          } else {
            debug_to_console("not success!");
          }
          $db->close();
          header("Location:login.php");
          exit();
        }
      }


    }

    function test_input($data) {
      $data = trim($data);
      $data = stripslashes($data);
      $data = htmlspecialchars($data);
      return $data;
    }

    function canSubmit($data){
      for ($x = 0; $x <= 6; $x++) {
          if(!$data[$x]){
            debug_to_console("cannot submit!");
            return false;
          }
      }
      debug_to_console("cannot submit!");
      return true;
    }

    function debug_to_console( $data ) {
        if ( is_array( $data ) )
            $output = "<script>console.log( 'Debug Objects: " . implode( ',', $data) . "' );</script>";
        else
            $output = "<script>console.log( 'Debug Objects: " . $data . "' );</script>";

        echo $output;
    }
    ?>

    <h2>Not Signed up yet?</h2>

    <form method="post">
      <p><span class="error">* required field.</span></p>
      First Name:     <input type="text" name="firstName" value="<?php echo $firstName;?>">
      <span class="error">* <?php echo $firstNameErr;?></span>
      <br><br>

      Last Name:     <input type="text" name="lastName" value="<?php echo $lastName;?>">
      <span class="error">* <?php echo $lastNameErr;?></span>
      <br><br>

      Username: <input type="text" name="username" value="<?php echo $username;?>">
      <span class="error">* <?php echo $usernameErr;?></span>
      <br><br>

      Password: <input type="text" name="passwd" value="<?php echo $passwd;?>">
      <span class="error">* <?php echo $passwdErr;?></span>
      <br><br>

      Re-enter Password: <input type="text" name="rePasswd" value="<?php echo $rePasswd;?>">
      <span class="error">* <?php echo $rePasswdErr;?></span>
      <br><br>

      E-mail: <input type="text" name="email" value="<?php echo $email;?>">
      <span class="error">* <?php echo $emailErr;?></span>
      <br><br>

      Phone Number:
      <input type="text" name="phone" value="<?php echo $phone;?>">
      <span class="error">* <?php echo $phoneErr;?></span>
      <br><br>

      Are you a librarian?
      <input type="checkbox" name="isLib" value="Yes" <?php if(preg_match("/^Yes$/", $isLib)) echo "checked = 'checked'";?>>
      <span class="error">* </span>
      <br><br>

      <input type="submit" name="submit" value="Submit">
    </form>
      <button type="button" name="button" value="Login" onclick="<?php header('Location:login.php');?>"></button>
  </body>

</html>
