<?php
    include("config.php");
    session_start();

    if($_REQUEST['postMethod'] == "s"){
      $shelfs = array(4);
      $i = 0;
      while($i < 4){
          $books = array();
           $sql = "SELECT books.* FROM shelves INNER JOIN books ON books.BookId=shelves.BookId WHERE shelfId='$i'";
           $result = $db->query($sql);
           while($row = $result->fetch_assoc()) {
               $books[] = array("bookName"=>$row["BookTitle"], "bookID"=>$row["BookId"], "author"=>$row["Author"], "isA"=>$row["Availability"]);
           }
           $shelfs[$i] = $books;
          $i++;
      }
      echo json_encode($shelfs);
    }

    function debug_to_console( $data ) {
           if ( is_array( $data ) )
               $output = "<script>console.log( 'Debug Objects: " . implode( ',', $data) . "' );</script>";
           else
               $output = "<script>console.log( 'Debug Objects: " . $data . "' );</script>";
    }

?>
