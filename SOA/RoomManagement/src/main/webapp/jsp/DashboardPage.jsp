<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">

  <title>INSA - Room Management</title>

  <!-- Custom fonts for this template-->
  <link href="${pageContext.request.contextPath}/jsp/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
  <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">

  <!-- Custom styles for this template-->
  <link href="${pageContext.request.contextPath}/jsp/css/sb-admin-2.min.css" rel="stylesheet">

</head>

<body id="page-top">

  <!-- Page Wrapper -->
  <div id="wrapper">

    <!-- Sidebar -->
    <ul class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion" id="accordionSidebar">

      <!-- Sidebar - Brand -->
      <a class="sidebar-brand d-flex align-items-center justify-content-center" href="#">
        <div class="sidebar-brand-icon">
        </div>
        <div class="sidebar-brand-text mx-3">Admin Page</div>
      </a>

      <!-- Divider -->
      <hr class="sidebar-divider my-0">

      <!-- Nav Item - Dashboard -->
      <li class="nav-item active">
        <a class="nav-link" href="index.html">
          <i class="fas fa-fw fa-tachometer-alt"></i>
          <span>Dashboard</span></a>
      </li>

      <!-- Divider -->
      <hr class="sidebar-divider">

      <!-- Heading -->
      <div class="sidebar-heading">
        Scenarios
      </div>

      <!-- Nav Item - Window -->
      <li class="nav-item">
        <a class="nav-link" href="#" data-toggle="popover" title="First scenario" data-content="If the temperature of a room is 5°C lower than the outside temperature, open the window to bring in the heat. If it is 5°C hotter, close the windows to keep the heat. If air quality is below 70%, open the windows regardless">
          <i class="fas fa-fw fa-table"></i>
          <span>Scenario 1</span></a>
      </li>

      <!-- Nav Item - Window -->
      <li class="nav-item">
        <a class="nav-link" href="#" data-toggle="popover" title="Second scenario" data-content="If the light sensor value drops below 0.4 in a room, turn on all the lights of the room. If it's above this value, turn them on. After 8PM and before 8AM, close every window and turn off every lamp.">
          <i class="fas fa-fw fa-table"></i>
          <span>Scenario 2</span></a>
      </li>


      <!-- Divider -->
      <hr class="sidebar-divider">



      <!-- Sidebar Toggler (Sidebar) -->
      <div class="text-center d-none d-md-inline">
        <button class="rounded-circle border-0" id="sidebarToggle"></button>
      </div>

    </ul>
    <!-- End of Sidebar -->

    <!-- Content Wrapper -->
    <div id="content-wrapper" class="d-flex flex-column">

      <!-- Main Content -->
      <div id="content">

        <!-- Topbar -->
        <nav class="navbar navbar-expand navbar-light bg-white topbar mb-4 static-top shadow">

          <!-- Sidebar Toggle (Topbar) -->
          <button id="sidebarToggleTop" class="btn btn-link d-md-none rounded-circle mr-3">
            <i class="fa fa-bars"></i>
          </button>



          <!-- Topbar Navbar -->
          <ul class="navbar-nav ml-auto">
              <li class="nav-item dropdown no-arrow d-sm-none">
                  <h2>Test</h2>
              </li>
    
            <!-- Nav Item - Searfh Dropdown (Visible Only XS) -->
            <li class="nav-item dropdown no-arrow d-sm-none">
              <a class="nav-link dropdown-toggle" href="#" id="searchDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                <h2>Test</h2>
              </a>
              <!-- Dropdown - Messages -->
              <div class="dropdown-menu dropdown-menu-right p-3 shadow animated--grow-in" aria-labelledby="searchDropdown">
                <form class="form-inline mr-auto w-100 navbar-search">
                  <div class="input-group">
                    <input type="text" class="form-control bg-light border-0 small" placeholder="Search for..." aria-label="Search" aria-describedby="basic-addon2">
                    <div class="input-group-append">
                      <button class="btn btn-primary" type="button">
                        <i class="fas fa-search fa-sm"></i>
                      </button>
                    </div>
                  </div>
                </form>
              </div>
            </li>

    

            <div class="topbar-divider d-none d-sm-block"></div>

            <!-- Nav Item - User Information -->
            <li class="nav-item dropdown no-arrow">
              <a class="nav-link dropdown-toggle" href="#" id="userDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                <span class="mr-2 d-none d-lg-inline text-gray-600 small">Maxime Arens - Vincent Erb - Marine Péfau - 5ISS A1</span>
              </a>
             
            </li>

          </ul>

        </nav>
        <!-- End of Topbar -->

        <!-- Begin Page Content -->
        <div class="container-fluid">

          <!-- Page Heading -->
          <div class="d-sm-flex align-items-center justify-content-between mb-4">
            <h1 class="h3 mb-0 text-gray-800">Dashboard</h1>
            <a id="date" href="#" class="d-none d-sm-inline-block btn btn-sm btn-primary shadow-sm">Fetching date ...</a>
          </div>

          <!-- Content Row -->
          <div class="row">

            <!-- Earnings (Monthly) Card Example -->
            <div class="col-xl-3 col-md-6 mb-4">
              <div class="card border-left-primary shadow h-100 py-2">
                <div class="card-body">
                  <div class="row no-gutters align-items-center">
                    <div class="col mr-2">
                      <div class="text-xs font-weight-bold text-primary text-uppercase mb-1">Number of rooms (GEI)</div>
                      <div id="nbRooms" class="h5 mb-0 font-weight-bold text-gray-800">-</div>
                    </div>
                    <div class="col-auto">
                      <i class="fas fa-calendar fa-2x text-gray-300"></i>
                    </div>
                  </div>
                </div>
              </div>
            </div>

   

            <!-- Earnings (Monthly) Card Example -->
            <div class="col-xl-3 col-md-6 mb-4">
              <div class="card border-left-info shadow h-100 py-2">
                <div class="card-body">
                  <div class="row no-gutters align-items-center">
                    <div class="col mr-2">
                      <div class="text-xs font-weight-bold text-info text-uppercase mb-1">Windows (open)</div>
                      <div class="row no-gutters align-items-center">
                        <div class="col-auto">
                          <div class="h5 mb-0 mr-3 font-weight-bold text-gray-800"><a id="nbWinOpen">-</a>/<a id="nbWinTotal">-</a></div>
                        </div>
                        <div class="col">
                          <div class="progress progress-sm mr-2">
                            <div class="progress-bar bg-info" role="progressbar" style="width: 100%" aria-valuenow="50" aria-valuemin="0" aria-valuemax="100"></div>
                          </div>
                        </div>
                      </div>
                    </div>
                    <div class="col-auto">
                      <i class="fas fa-clipboard-list fa-2x text-gray-300"></i>
                    </div>
                  </div>
                </div>
              </div>
            </div>

            <!-- Pending Requests Card Example -->
            <div class="col-xl-3 col-md-6 mb-4">
              <div class="card border-left-warning shadow h-100 py-2">
                <div class="card-body">
                  <div class="row no-gutters align-items-center">
                    <div class="col mr-2">
                      <div class="text-xs font-weight-bold text-warning text-uppercase mb-1">Outside temperature</div>
                      <div id="outTemp" class="h5 mb-0 font-weight-bold text-gray-800">-°C</div>
                    </div>
                    <div class="col-auto">
                      <i class="fas fa-comments fa-2x text-gray-300"></i>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <!-- Content Row -->

          <div class="row">

            <!-- Area Chart -->
            <div class="col-xl-8 col-lg-7">
              <div class="card shadow mb-4">
              <div class="card-header py-3">
                	<h6 class="m-0 font-weight-bold text-primary">Building Overview</h6>
              </div>
                <div class="card-body mb-4">
                	
                	<div class="row">
	                  <div class="col-xl-6 col-lg-4">
	                  	<div class="h4 mb-4 font-weight-bold text-gray-800">Room 1</div>
	                  	<ul>
	                  		<li class="h5 mb-4">Temperature : <b><a id="temproom1">-</a></b></li>
	                  		<li class="h5 mb-4">Air quality : <b><a id="gasroom1">-</a></b></li>
	                  		<li class="h5 mb-1">Window 1 : <b><a id="win1room1">-</a></b></li>
	                  		<li class="h5 mb-5">Window 2 : <b><a id="win2room1">-</a></b></li>
	                  		<li class="h5 mb-1">Light intensity : <b><a id="lightroom1">-</a></b></li>
	                  		<li class="h5 mb-1">Lamps : <b><a id="lampsroom1">-/-</a> </b></li>
	                  	</ul>
	                  </div>
	                  <div class="col-xl-6 col-lg-4">
	                  	<div class="h4 mb-4 font-weight-bold text-gray-800">Room 2</div>
	                  	<ul>
	                  		<li class="h5 mb-4">Temperature : <b><a id="temproom2">-</a></b></li>
	                  		<li class="h5 mb-4">Air quality : <b><a id="gasroom2">-</a></b></li>
	                  		<li class="h5 mb-1">Window 1 : <b><a id="win1room2">-</a></b></li>
	                  		<li class="h5 mb-5">Window 2 : <b><a id="win2room2">-</a></b></li>
	                  		<li class="h5 mb-1">Light intensity : <b><a id="lightroom2">-</a></b></li>
	                  		<li class="h5 mb-1">Lamps : <b><a id="lampsroom2">-/-</a> </b></li>
	                  	</ul>
	                  </div>
	                </div>
                </div>
              </div>
            </div>

            <!-- Pie Chart -->
            <div class="col-xl-4 col-lg-5">
              <div class="card shadow mb-4">
                <!-- Card Header - Dropdown -->
                <div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
                  <h6 class="m-0 font-weight-bold text-primary">API Status</h6>
                 
                </div>
                <!-- Card Body -->
                <div class="card-body">
                  <h4 class="small font-weight-bold">OM2M IN-CSE<span class="float-right">OK</span></h4>
                  <div class="progress mb-4">
                    <div class="progress-bar bg-success" role="progressbar" style="width: 100%" aria-valuenow="20" aria-valuemin="0" aria-valuemax="100"></div>
                  </div>
                  <h4 class="small font-weight-bold">MN-CSE Room 1<span class="float-right">OK</span></h4>
                  <div class="progress mb-4">
                    <div class="progress-bar bg-success" role="progressbar" style="width: 100%" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100"></div>
                  </div>
                  <h4 class="small font-weight-bold">MN-CSE Room 2<span class="float-right">OK</span></h4>
                  <div class="progress mb-4">
                    <div class="progress-bar bg-success" role="progressbar" style="width: 100%" aria-valuenow="100" aria-valuemin="0" aria-valuemax="100"></div>
                  </div>
                  <h4 class="small font-weight-bold">OpenWeatherMap<span class="float-right">OK</span></h4>
                  <div class="progress mb-4">
                    <div class="progress-bar bg-success" role="progressbar" style="width: 100%" aria-valuenow="100" aria-valuemin="0" aria-valuemax="100"></div>
                  </div>
                  <h4 class="small font-weight-bold">WorldTime&Date<span class="float-right">OK</span></h4>
                  <div class="progress">
                    <div class="progress-bar bg-success" role="progressbar" style="width: 100%" aria-valuenow="100" aria-valuemin="0" aria-valuemax="100"></div>
                  </div>
                
              </div>
            </div>
          </div>


        </div>
        <!-- /.container-fluid -->
        
        
		<div class="card shadow mb-4">
	    	<div class="card-header py-3">
           		<h6 class="m-0 font-weight-bold text-primary">Scenario parameters</h6>
       		</div>
       		<div class="card-body mb-1">
       		<div class="row mb-3">
				<div class="col-2">
       	   			<div class="input-group">
				  	<div class="input-group-prepend">
				    	<span class="input-group-text" id="inputGroup-sizing-default">Room</span>
				  	</div>
				  	<input id="roomSett" type="text" class="form-control" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default">
				  	</div>
       	   		</div>
       	   		<div class="col-3">
       	   			<div class="input-group">
				  	<div class="input-group-prepend">
				    	<span class="input-group-text" id="inputGroup-sizing-default">Temperature</span>
				  	</div>
				  	<input id="tempeSet" type="text" class="form-control" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default">
				  	</div>
       	   		</div>

       	   		<div class="col-sm">
       	   			<a class="btn btn-info" href="#" id="btnSetTempe">
				  		Set temperature
					</a>
       	   		</div> 			
       		</div>
       		
       		<div class="row mb-3">
				<div class="col-2">
       	   			<div class="input-group">
				  	<div class="input-group-prepend">
				    	<span class="input-group-text" id="inputGroup-sizing-default">Room</span>
				  	</div>
				  	<input id="roomSetg" type="text" class="form-control" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default">
				  	</div>
       	   		</div>
       	   		<div class="col-3">
       	   			<div class="input-group">
				  	<div class="input-group-prepend">
				    	<span class="input-group-text" id="inputGroup-sizing-default">Air quality</span>
				  	</div>
				  	<input id="gasSet" type="text" class="form-control" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default">
				  	</div>
       	   		</div>

       	   		<div class="col-sm">
       	   			<a class="btn btn-info" href="#" id="btnSetGas">
				  		Set air quality
					</a>
       	   		</div> 			
       		</div>
       		
       		<div class="row">
				<div class="col-2">
       	   			<div class="input-group">
				  	<div class="input-group-prepend">
				    	<span class="input-group-text" id="inputGroup-sizing-default">Room</span>
				  	</div>
				  	<input id="roomSetl" type="text" class="form-control" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default">
				  	</div>
       	   		</div>
       	   		<div class="col-3">
       	   			<div class="input-group">
				  	<div class="input-group-prepend">
				    	<span class="input-group-text" id="inputGroup-sizing-default">Light intensity</span>
				  	</div>
				  	<input id="lightSet" type="text" class="form-control" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default">
				  	</div>
       	   		</div>

       	   		<div class="col-sm">
       	   			<a class="btn btn-info" href="#" id="btnSetLight">
				  		Set light intensity
					</a>
       	   		</div> 			
       		</div>
       		
       	</div>
      </div>
      <!-- End of Main Content -->

      <!-- Footer -->
      <footer class="sticky-footer bg-white">
        <div class="container my-auto">
          <div class="copyright text-center my-auto">
            <span>Copyright &copy; Maxime Arens, Vincent Erb & Marine Péfau 2019</span>
          </div>
        </div>
      </footer>
      <!-- End of Footer -->

    </div>
    <!-- End of Content Wrapper -->

  </div>
  <!-- End of Page Wrapper -->

  <!-- Scroll to Top Button-->
  <a class="scroll-to-top rounded" href="#page-top">
    <i class="fas fa-angle-up"></i>
  </a>


  <!-- Bootstrap core JavaScript-->
  <script src="${pageContext.request.contextPath}/jsp/vendor/jquery/jquery.min.js"></script>
  <script src="${pageContext.request.contextPath}/jsp/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

  <!-- Core plugin JavaScript-->
  <script src="${pageContext.request.contextPath}/jsp/vendor/jquery-easing/jquery.easing.min.js"></script>

  <!-- Custom scripts for all pages-->
  <script src="${pageContext.request.contextPath}/jsp/js/sb-admin-2.min.js"></script>

  <!-- Page level plugins -->
  <script src="${pageContext.request.contextPath}/jsp/vendor/chart.js/Chart.min.js"></script>

  <!-- Page level custom scripts -->
  <script src="${pageContext.request.contextPath}/jsp/js/demo/chart-area-demo.js"></script>
  <script src="${pageContext.request.contextPath}/jsp/js/demo/chart-pie-demo.js"></script>
  
  <!-- Custom scripts -->
  <script src="${pageContext.request.contextPath}/jsp/js/main.js"></script>

  <script>
	  $(document).ready(function(){
	      $('[data-toggle="popover"]').popover();   
	  });
  </script>

</body>
</html>