//jquery-click-scroll
//by syamsul'isul' Arifin

var sectionArray = [1, "about", "events", "contact_us"];

$.each(sectionArray, function(index, value){
          
     $(document).scroll(function(){
         if (window.location.pathname !== "/"){
            return;
         }


         var offsetSection = $('#' + 'section_' + value).offset().top - 94;
         var docScroll = $(document).scrollTop();
         var docScroll1 = docScroll + 1;
         
        
         if ( docScroll1 >= offsetSection ){
             $('.navbar-nav .nav-item .nav-link').removeClass('active');
             $('.navbar-nav .nav-item .nav-link:link').addClass('inactive');  
             $('.navbar-nav .nav-item .nav-link').eq(index).addClass('active');
             $('.navbar-nav .nav-item .nav-link').eq(index).removeClass('inactive');
         }
         
     });
    
    $('.click-scroll').eq(index).click(function(e){
        if (value === 1 && window.location.pathname !== "/"){
            return;
        }

        var offsetClick = $('#' + 'section_' + value).offset().top - 94;
        // if w click on te home and we are in the main page
        // we don't want to scroll to the top of the page
        if (value === 1 && window.location.pathname !== "/"){
            window.location.href = "/";
        }
        e.preventDefault();
        $('html, body').animate({
            'scrollTop':offsetClick
        }, 300)
    });
    
});

$(document).ready(function(){

    $('.navbar-nav .nav-item .nav-link:link').addClass('inactive');
    if (window.location.pathname === "/") {
        $('.navbar-nav .nav-item .nav-link').eq(0).addClass('active');
        $('.navbar-nav .nav-item .nav-link:link').eq(0).removeClass('inactive');
    }
    else if (window.location.pathname === "/join-us") {
        $('#membership_nav').addClass('active');
        $('#membership_nav').removeClass('inactive');

    } else if (window.location.pathname === "/event-listing") {
        $('#events_listing_nav').addClass('active');
        $('#events_listing_nav').removeClass('inactive');
    }
});