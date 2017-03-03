


$(document).ready(function(){

		$(window).resize(function(){
			if($(window).width() >= 767){
				$(".sidebar").slideDown('350');
			}
			else{
				$(".sidebar").slideUp('350');
			}
		});
	
	 $(".navigation a").click(function(){
	  
		if(!$(this).hasClass("dropy")) {
			// hide any open menus and remove all other classes
			$(".sidebar").slideUp('350');
			$(".navigation a").removeClass("dropy");
			
			// open our new menu and add the dropy class
			$(".sidebar").slideDown('350');
			$(this).addClass("dropy");
		}
		else if($(this).hasClass("dropy")) {
			$(this).removeClass("dropy");
			$(".sidebar").slideUp('350');
		}
	});
});

	
	$('.service-item').waypoint(function(down) {
		$(this).addClass('animation');
		$(this).addClass('fadeInUp');
	}, { offset: '70%' });
	
	
	$('.feature-item').waypoint(function(down) {
		$(this).addClass('animation');
		$(this).addClass('fadeInUp');
	}, { offset: '70%' });
	
	$('.testimonial-content').waypoint(function(down) {
		$(this).addClass('animation');
		$(this).addClass('fadeInUp');
	}, { offset: '70%' });

	/* Scroll to Top */


	  $(".totop").hide();

	  $(function(){
		$(window).scroll(function(){
		  if ($(this).scrollTop()>300)
		  {
			$('.totop').slideDown();
		  } 
		  else
		  {
			$('.totop').slideUp();
		  }
		});

		$('.totop a').click(function (e) {
		  e.preventDefault();
		  $('body,html').animate({scrollTop: 0}, 500);
		});

	  });
  
