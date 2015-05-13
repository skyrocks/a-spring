function Map() {

    var size = 0;

    var entry = new Object();

    this.put = function(key, value) {
        if (!this.containsKey(key)) {
           size++;
        }
        entry[key] = value;
    }

    this.get = function(key) {
        if (this.containsKey(key)) {
            return entry[key];
        } else {
            return null;
        }
    }

    this.remove = function(key) {
        if (delete entry[key]) {
            size--;
        }
    }

    this.containsKey = function(key) {
        return (key in entry);
    }

    this.containsValue = function(value) {
        for (var prop in entry) {
            if (entry[prop] == value) {
                return true;
            }
        }
        return false;
    }

    this.values = function() {
        var values = [];
        for (var prop in entry) {
            values.push(entry[prop]);
        }
        return values;
    }

    this.keys = function() {
        var keys = [];
        for (var prop in entry) {
            keys.push(prop);
         }
        return keys;
    }

    this.size = function() {
        return size;
    }
    
    this.clear = function(){
    	entry = new Object();
    }
}