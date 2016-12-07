var files;

var mStl = {
    "meshes": {}
};

var vertices = [];

var startDrawing = false;

////////////////////////////////////////////////////////////////////////////////
//Drag and Drop functions
////////////////////////////////////////////////////////////////////////////////
function handleFileSelect(mEvent) {
    mEvent.stopPropagation();
    mEvent.preventDefault();

    files = mEvent.dataTransfer.files; // FileList object.

    //List some properties.
    document.getElementById('list').innerHTML =
        '<p><strong>' + files[0].name + '</strong> - ' +
        files[0].size + ' bytes, last modified: ' +
        (files[0].lastModifiedDate ? files[0].lastModifiedDate.toLocaleDateString() : 'n/a') +
        '</p>';

    var add = addElements();

    startDrawing = true;

};

function handleDragOver(mEvent) {
    mEvent.stopPropagation();
    mEvent.preventDefault();
    mEvent.dataTransfer.dropEffect = 'copy'; // Explicitly show this is a copy.
};

// Setup the dnd listeners.
var dropZone = document.getElementById('drop_zone');
dropZone.addEventListener('dragover', handleDragOver, false);
dropZone.addEventListener('drop', handleFileSelect, false);

////////////////////////////////////////////////////////////////////////////////
//Parse File functions (.stl to .JSON)
////////////////////////////////////////////////////////////////////////////////
//parse and convert to vertices
function addElements() {

    mStl.meshes.vertices = [];
    mStl.meshes.normals = [];
    mStl.meshes.texturecoords = [];
    mStl.meshes.faces = [];

    var file = this.files[0];

    var reader = new FileReader();
    reader.onload = function(progressEvent) {
        var lines = this.result.split('\n');
        for (var l = 0, i; l < lines.length; l++) {
            i = lines[l].trim();
            if (i.startsWith("vertex")) {
                var mChar = parseVertices(i);
            } else if (i.startsWith("facet normal")) {
                var mChar = parseNormal(i);
            }
        }
        var texture = generateTexturecoords();
        var face = generateFaces();
        console.log("number of vertices: " + mStl.meshes.vertices.length);
        console.log("number of normals: " + mStl.meshes.normals.length);
        console.log("number of faces: " + mStl.meshes.faces.length);
        console.log("number of texturecoords: " + mStl.meshes.texturecoords.length);
        console.log(JSON.stringify(mStl));
    };
    reader.readAsText(file);
    return true;
};

//calculate vertices
function parseVertices(line) {
    line = line.substring(9, line.length);
    var words = line.split(" ");
    for (var i = 0, v; i < words.length; i++) {
        var num = Number(words[i]);
        v = num.toFixed(6);
        mStl.meshes.vertices.push(v);
    }
    return true;
};

//calculate normals
function parseNormal(line) {
    line = line.substring(13, line.length);
    var words = line.split(" ");
    for (var i = 0, n; i < words.length; i++) {
        var num = Number(words[i]);
        n = num.toFixed(6);
        mStl.meshes.normals.push(n);
    }
    return true;
};
//calculate faces
function generateFaces() {
    //base case
    mStl.meshes.faces.push([0, 1, 2]);
    mStl.meshes.faces.push([2, 1, 3]);

    //iterations
    for (var f = 2, v; f < mStl.meshes.vertices.length / 3; f++) {
        var three = new Array('3');

        //first point of face
        if (f % 2 == 0) {
            three[0] = f / 2 * 3;
        } else {
            three[0] = 2 + (f - 1) / 2 * 3;
        }


        //third point of face
        if (f % 4 == 0) {
            var temp = mStl.meshes.faces[mStl.meshes.faces.length - 1][2] + 4;
            three[2] = temp;
        } else if ((f + 1) % 4 == 0) {
            var temp = mStl.meshes.faces[mStl.meshes.faces.length - 1][2];
            three[2] = temp;
        } else {
            var temp = mStl.meshes.faces[mStl.meshes.faces.length - 1][2] + 1;
            three[2] = temp;
        }

        //second point of face
        if (f % 4 == 0) {
            var temp = mStl.meshes.faces[mStl.meshes.faces.length - 1][1] + 4;
            three[1] = temp;
        } else if ((f + 1) % 4 == 0) {
            var temp = mStl.meshes.faces[mStl.meshes.faces.length - 1][1] + 2;
            three[1] = temp;
        } else {
            var temp = mStl.meshes.faces[mStl.meshes.faces.length - 1][1];
            three[1] = temp;
        }

        //add the three numbers to faces array
        mStl.meshes.faces.push(three);
    }
    return true;
};

//generate random texturecoords
function generateTexturecoords() {
    for (var i = 0, v; i < mStl.meshes.vertices.length / 3 * 4; i++) {
        var t = Math.random();
        mStl.meshes.texturecoords.push(t);
    }

    return true;
};

////////////////////////////////////////////////////////////////////////////////
//Loader functions
////////////////////////////////////////////////////////////////////////////////
// Load a text resource
var loadTextResource = function(url, callback) {
    var request = new XMLHttpRequest();
    request.open('GET', url + '?date=' + new Date(), true);
    request.onload = function() {
        if (request.status < 200 || request.status > 299) {
            callback('Error: HTTP Status ' + request.status + ' on resource ' + url);
        } else {
            callback(null, request.responseText);
        }
    };
    request.send();
};
// Load an image
var loadImage = function(url, callback) {
    var image = new Image();
    image.onload = function() {
        callback(null, image);
    };
    image.src = url;
};

// Load a JSON object
var loadJSONResource = function(url, callback) {
    loadTextResource(url, function(err, result) {
        if (err) {
            callback(err);
        } else {
            try {
                callback(null, JSON.parse(result));
            } catch (e) {
                callback(e);
            }
        }
    });
};
