'use strict'

/* eslint */
/* global _, $, FileReader, OpenALPRAdapter */

/**
 * init
 */
function OpenALPRAdapterDemo () {
  // Adapters
  var adapters = {
    'start': function () {
      return start()
    }
  }


  var start = function () {
    $(document).on('change', '#fileL', function (input) {
      runOpenALPR(input)
    })
  }

  var runOpenALPR = function (input) {
    checkAndReadImage(input)
  }

  var checkAndReadImage = function (input) {
    if (!window.FileReader) {
      alert('Browser does not support FileReader!')
      return
    }
    if (input.currentTarget.files && input.currentTarget.files[0]) {
      var reader = new FileReader()
      
      reader.onload = function (e) {
        var imageDataURL = e.target.result
        OpenALPRAdapter().retrievePlate(imageDataURL)
          .then(function (response) {
            cloudAPISuccess(response)
          })
          .catch(function (err) {
            cloudAPIError(err)
          })
      }
      reader.readAsDataURL(input.currentTarget.files[0])
    }
  }
  
  
  var cloudAPIError = function (err) {
    if (err && err.responseText) {
      alert()('Failed to retrieve data ' + err.responseText)
    } else {
      alert('Exception occured', err)
    }
  }

  var cloudAPISuccess = function (response) {
    if (!response.results || response.results.length === 0) {
      alert('No plate found in image.')
      return
    }
    //TODO inserire targa nell'input
      var result = response.results[0]
      alert('Plate: ' + result.plate)
      $("input[name='car']").val(result.plate);
  }

  // Return adapters (must be at end of adapter)
  return adapters
}

window.exports = OpenALPRAdapterDemo
// End A (Adapter)

$(document).ready(function () {
  OpenALPRAdapterDemo().start()
})
