package com.nnk.springboot;

import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * @author trimok
 *
 */
/**
 * A filter class for logging requests and responses
 */
@Slf4j
@Component
public class LoggingFilterBean extends GenericFilterBean {

    /**
     * The filtering
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
	    throws IOException, ServletException {
	ContentCachingRequestWrapper requestWrapper = requestWrapper(request);
	ContentCachingResponseWrapper responseWrapper = responseWrapper(response);

	chain.doFilter(requestWrapper, responseWrapper);

	logRequest(requestWrapper);
	logResponse(responseWrapper);
    }

    /**
     * Logging the request
     * 
     * @param request : the ContentCachingRequestWrapper request
     */
    private void logRequest(ContentCachingRequestWrapper request) {
	StringBuilder builder = new StringBuilder();
	builder.append(request.getMethod() + " ");
	builder.append(request.getRequestURI() + " ");
	builder.append(new String(request.getContentAsByteArray()));
	// Parameters for GET
	int sizeParameterMap = request.getParameterMap().size();
	if (sizeParameterMap > 0) {
	    int iParameter = 0;
	    builder.append("{");
	    for (Map.Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
		builder.append("\"" + entry.getKey() + "\"" + ":");
		for (String info : entry.getValue()) {
		    builder.append("\"" + info + "\"");
		}
		if (iParameter < sizeParameterMap - 1) {
		    builder.append(",");
		} else {
		    builder.append("}");
		}

		++iParameter;
	    }
	}
	log.info("Request: {}", builder);
    }

    /**
     * Logging the response
     * 
     * @param response : the ContentCachingResponseWrapper response
     * @throws IOException : a possible IOException
     */
    private void logResponse(ContentCachingResponseWrapper response) throws IOException {
	StringBuilder builder = new StringBuilder();
	builder.append(new String(response.getContentAsByteArray()));

	if (builder.lastIndexOf("error") > -1) {
	    // log.error("Response: {}", builder);
	    log.error("Error in Response");
	} else {
	    // log.info("Response: {}", builder);
	    log.info("Response OK");
	}
	response.copyBodyToResponse();
    }

    /**
     * Building a new ContentCachingRequestWrapper if necessary
     * 
     * @param request : a Servlet request
     * @return : the ContentCachingRequestWrapper object
     */
    private ContentCachingRequestWrapper requestWrapper(ServletRequest request) {
	if (request instanceof ContentCachingRequestWrapper requestWrapper) {
	    return requestWrapper;
	}
	return new ContentCachingRequestWrapper((HttpServletRequest) request);
    }

    /**
     * * Building a new ContentCachingResponseWrapper if necessary
     * 
     * @param response : a ServletResponse object
     * @return : a ContentCachingResponseWrapper object
     */
    private ContentCachingResponseWrapper responseWrapper(ServletResponse response) {
	if (response instanceof ContentCachingResponseWrapper responseWrapper) {
	    return responseWrapper;
	}
	return new ContentCachingResponseWrapper((HttpServletResponse) response);
    }
}
