function mySnackbar() {
			var o = document.getElementById("mySnackbar");
			o.className = "show";
			setTimeout(function(){ o.className = o.className.replace("show","");},3700);
			}