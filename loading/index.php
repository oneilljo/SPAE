<!DOCTYPE html>
<html>
<title>SCRATCH Thesis Project</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="../css/format.css">
<link rel="stylesheet" href="../css/font.css">
<link rel="stylesheet" href="../css/style.css">
<link rel="icon" href="../images/favicon.ico" type="image/x-icon">
<head>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
</head>
<body onload="document.download.submit()" >
  <div class="bgimg w3-display-container w3-animate-opacity w3-text-white">
    <div class="w3-display-topright w3-padding-small w3-medium">
      <a href="../">Home</a> | <a href="../about/">About</a> | <a href="../login/">Login</a> | <a href="../signup/">Sign Up</a>
    </div>
    <div class="w3-display-middle w3-center">
      <img class= "w3-animate-top" src="../images/scratch.png" alt = "Title">
      </br>
      <p>Downloading and processing your project...</p>
      </br>
      </br>
      <img class= "w3-animate-top" style="width:50px;height:50px;"  src="../images/loading.gif" alt = "Loading">
      <form name="download" method="post" action="/scratch/loading/download.php">
        <input type="hidden" name="project" value="<?php echo $_POST['project'] ?>" />
      </form>
    </div>
    <div class="w3-display-bottomleft w3-padding-small w3-tiny">
    Created &amp; Mantained by Joseph O'Neill. Website Repository can be found <a href="https://github.com/oneilljo/SCRATCH_Analysis" target="_blank">here</a>.
    </div>
  </div>
</body>
</html>
