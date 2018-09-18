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
  <?php exec("./projectdownloader.py " . $_POST["project"]); ?>
  <?php exec("chmod 777 project-" . $_POST["project"] . ".sb2"); ?>
  <?php exec("chown apache:apache project-" . $_POST["project"] . ".sb2"); ?>
  <?php exec("cp project-" . $_POST["project"] . ".sb2 ../savedProjects"); ?>
  <?php exec("mv -f project-" . $_POST["project"] . ".sb2 ../SCATT/submissions/project-" . $_POST["project"] . ".sb2"); ?>
  <?php exec("/usr/bin/java -jar ../SCATT/Scatt.jar"); ?>
  <?php exec("rm -f ../SCATT/submissions/project-" . $_POST["project"] . ".sb2"); ?>
  <?php exec("rm -rf zips/" . $_POST["project"] . ".zip"); ?>
  <?php exec("mv unzips/project-" . $_POST["project"] . "/project.json ../savedReports/project-" . $_POST["project"]); ?>
  <?php exec("rm -rf unzips/" . $_POST["project"]); ?>
  window.location = "../progress/index.php?projectid=" + projectID;
  }
  </script>
</head>
<body onload="download()">
  <div class="bgimg">
  </div>
</body>
</html>
