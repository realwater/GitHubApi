<%@ page session="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	<head>
		<title>Home</title>
		<script type="text/javascript">
			function opener() {
				// 팝업 창
				window.open('/opener.do','target_name','scrollbars=no,toolbar=no,resizable=no,width="500",heigth="300",left=0,top=0');
			}	
		</script>		
	</head>
	<body>
		<button onclick="opener();">GIT 로그인</button>		
	</body>
</html>
