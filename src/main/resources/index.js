function testRequest(type, withHeader, test) {
	var xhr = new XMLHttpRequest();
	const requestUrl = test ? `http://localhost:8080/cors/test?type=${type}&test=2`:`http://localhost:8080/cors/test?type=${type}`;
	xhr.open('GET', requestUrl, true);
	xhr.withCredentials = true;
	if(withHeader) {
		xhr.setRequestHeader('Content-Type', 'application/json;charset=UTF-8');
    	xhr.setRequestHeader('test', window.location.href);
    }
	xhr.send();
	xhr.onreadystatechange = function() {
	    if(this.readyState === 4) {
			console.log(`respose: ${xhr.responseText}`);
		}
		if(this.readyState === this.HEADERS_RECEIVED) {
			console.log(`test header: ${xhr.getResponseHeader("test")}`);
		}
	}
}
