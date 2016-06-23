var dayDate = "";
var customerName = "";
var customerPhone = "";

function openModal(formDayDate) {
    dayDate = formDayDate;
    if(customerName != "" && customerPhone != "") {
        $(".modal #customerName").val(customerName);
        $(".modal #customerPhone").val(customerPhone);
    }
    $('#customerDataModal').modal('show');
}

function preOrder() {
    //simple validation first
    var phone = new RegExp("^(?:\\+\\d{1,3}|0\\d{1,3}|00\\d{1,2})(?:\\s?\\(\\d+\\))?(?:[-\\/\\s.]|\\d)+$");
    if( $(".modal #customerName").val() != "" &&
        phone.test($(".modal #customerPhone").val()) ) {
            customerName = $(".modal #customerName").val();
            customerPhone = $(".modal #customerPhone").val();
            var orderArray = $("form[name='" + dayDate + "']").serializeArray();
            if(orderArray.length !== 0) {
                var orderObj = {
                    customer: {
                        name: customerName,
                        phone: customerPhone
                    },
                    order: orderArray
                };
                $.post("/menu/daily", orderObj, function(data) {
                    console.log(data);
                });
            } else {
                $(".modal .wrong-data").text("You've not ordered any food.").fadeIn().delay(2000).fadeOut(function() {
                    $('#customerDataModal').modal('hide');
                });
            }
        }
    else {
        $(".modal .wrong-data").text("You've filled wrong data. Please try to repair it.").fadeIn().delay(2000).fadeOut();
    }
}
