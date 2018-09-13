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
  <script defer>
  function download() {
  var projectID = "<?php echo $_POST['project']?>";
  var elem = document.getElementById("myBar");
  var width = 1;
  var id = setInterval(frame, 30);
  var progress = 0;
  <?php exec("./projectdownloader.py " . $_POST["project"]); ?>
  progress = progress + 10;
  <?php exec("chmod 777 project-" . $_POST["project"] . ".sb2"); ?>
  progress = progress + 10;
  <?php exec("chown apache:apache project-" . $_POST["project"] . ".sb2"); ?>
  progress = progress + 10;
  <?php exec("cp project-" . $_POST["project"] . ".sb2 ../savedProjects"); ?>
  progress = progress + 10;
  <?php exec("mv -f project-" . $_POST["project"] . ".sb2 ../SCATT/submissions/project-" . $_POST["project"] . ".sb2"); ?>
  progress = progress + 10;
  <?php exec("/usr/bin/java -jar ../SCATT/Scatt.jar"); ?>
  progress = progress + 10;
  <?php exec("rm -f ../SCATT/submissions/project-" . $_POST["project"] . ".sb2"); ?>
  progress = progress + 10;
  <?php exec("rm -rf zips/" . $_POST["project"] . ".zip"); ?>
  progress = progress + 10;
  <?php exec("mv unzips/project-" . $_POST["project"] . "/project.json ../savedReports/project-" . $_POST["project"]); ?>
  progress = progress + 10;
  <?php exec("rm -rf unzips/" . $_POST["project"]); ?>
  progress = progress + 10;

  function frame() {
    if (width >= 100) {
      clearInterval(id);
      window.location = "../progress/index.php?projectid=" + projectID;
    } else {
      width = progress;
      elem.style.width = width + '%';
    }
  }
  }
  download();
  </script>
</head>
<body>
  <div class="bgimg w3-display-container w3-animate-opacity w3-text-white">
    <div class="w3-display-topright w3-padding-small w3-medium">
      <a href="../">Home</a> | <a href="../about/">About</a> | <a href="../login/">Login</a> | <a href="../signup/">Sign Up</a>
    </div>
    <div class="w3-display-middle w3-center">
      <img class= "w3-animate-top" src="../images/scratch.png" alt = "Title">
      </br>
      <div class="w3-light-grey w3-round-xlarge w3-animate-bottom">
        <div id="myBar" class="w3-container w3-blue w3-round-xlarge" style="height:24px;width:1%"></div>
      </div>
    </div>
    <div class="w3-display-bottomleft w3-padding-small w3-tiny">
    Created &amp; Mantained by Joseph O'Neill. Website Repository can be found <a href="https://github.com/oneilljo/SCRATCH_Analysis" target="_blank">here</a>.
    </div>
  </div>
</body>
</html>
