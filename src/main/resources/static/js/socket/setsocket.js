function startSocket(){
    $.ajax({
             url:'/startsocket',
             method:'GET',
             success: function(data){
                   console.log(data);
                 },
              error: function (err) {
                    console.log(err);
               }
               });
}


function stopSocket(){
    $.ajax({
        url:'/stopsocket',
        method:'GET',
        success: function(data){
                  if(data) {
                  console.log("success");
              }
           },
        error: function (err) {
                    console.log("faliure");
        }
     });
}