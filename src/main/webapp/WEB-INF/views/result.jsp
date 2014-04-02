<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	<head>
		<title>Home</title>
		<link rel="stylesheet" href="/js/lib/codemirror.css">
		<script type="text/javascript" src="/js/lib/codemirror.js"></script>	
	</head>
	<body>
		<textarea id="code" readonly>${result}</textarea>				
	</body>
	<script type="text/javascript">
      var editor = CodeMirror.fromTextArea(document.getElementById("code"), {
        lineNumbers: true,
        mode: "text/html"
      });
    </script>
</html>
