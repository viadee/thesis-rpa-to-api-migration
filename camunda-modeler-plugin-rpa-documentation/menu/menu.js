'use strict';

module.exports = function(electronApp, menuState) {
  return [{
    label: 'RPA Documentation Plugin',
    enabled: function() {
      return true;
    },
    action: function() {
    }
  }];
};
