var dayDate = customerName = customerPhone = "";
var finalPrice = [0, 0, 0, 0, 0];

function openModal(formDayDate) {
    dayDate = formDayDate;
    if(customerName != "" && customerPhone != "") {
        $(".modal #customerName").val(customerName);
        $(".modal #customerPhone").val(customerPhone);
    }
    $('#customerDataModal').modal('show');
}

function clearForm(formDayDate) {
    $("form[name='" + formDayDate + "'] input[type='radio']").removeAttr("checked");
    $("form[name='" + formDayDate + "'] .preorder-btn-wrapper span").empty();
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
                    date: dayDate,
                    order: orderArray
                };
                $.post("/menu/daily", orderObj, function(data) {
                    if(data == 1) {
                        //success
                        $("#customerDataModal #preorderBtn").attr("disabled", true);
                        $(".modal .good-data").text("Your preorder was successful.").fadeIn().delay(1200).fadeOut(function() {
                            finalPrice = [0, 0, 0, 0, 0];
                            for(var i = 0; i < 5; i++) {
                                $("#finalPrice" + i).empty();
                            }
                            $('#customerDataModal').modal('hide');
                            $("#customerDataModal #preorderBtn").removeAttr("disabled");
                            $(".foodVariants input[type='radio']").removeAttr("checked");
                        });
                    } else {
                        $(".modal .wrong-data").text("You cannot make more orders on the same day.").fadeIn().delay(3000).fadeOut();
                    }
                });
            } else {
                $(".modal .wrong-data").text("You've not ordered any food.").fadeIn().delay(1500).fadeOut(function() {
                    $('#customerDataModal').modal('hide');
                });
            }
        }
    else {
        $(".modal .wrong-data").text("You've filled wrong data. Please try to repair it.").fadeIn().delay(3000).fadeOut();
    }
}

function sum(pricesElements) {
    var sum = 0;
    pricesElements.each(function() {
        sum += parseInt($(this).text());
    });
    return sum;
}

function disablePassedDays() {
    var today = new Date();
    $("form").each(function() {
        var formDate = new Date($(this).attr("name"));
        if(today > formDate) {
            $(this).find("input").attr("disabled", true);
            $(this).find("button").attr("disabled", true);
        }
    });
}

$(document).ready(function() {

    disablePassedDays();

    // Deselect all radio buttons on page refresh (F5)
    $("input[type='radio']").removeAttr("checked");

    // Show the final price of the day, when the user click on some radio button
    $("input[type='radio']").click(function() {
        var form = $(this).closest("form");
        var checkedRadios = form.find("input[type='radio']:checked");
        switch(form.attr("id")) {
            case "monday": finalPrice[0] = sum(checkedRadios.siblings("span").find(".priceValue")); break;
            case "tuesday": finalPrice[1] = sum(checkedRadios.siblings("span").find(".priceValue")); break;
            case "wednesday": finalPrice[2] = sum(checkedRadios.siblings("span").find(".priceValue")); break;
            case "thursday": finalPrice[3] = sum(checkedRadios.siblings("span").find(".priceValue")); break;
            case "friday": finalPrice[4] = sum(checkedRadios.siblings("span").find(".priceValue")); break;
        }

        for(var i = 0; i < 5; i++) {
            if(finalPrice[i] != 0) {
                $("#finalPrice" + i).text("Celkem: " + finalPrice[i] + " Kč");
            }
        }
    });

});
