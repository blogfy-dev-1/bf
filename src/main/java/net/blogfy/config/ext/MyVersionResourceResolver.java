package net.blogfy.config.ext;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.io.AbstractResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.resource.HttpResource;
import org.springframework.web.servlet.resource.ResourceResolverChain;
import org.springframework.web.servlet.resource.VersionResourceResolver;
import org.springframework.web.servlet.resource.VersionStrategy;

public class MyVersionResourceResolver extends VersionResourceResolver {
	
	@Override
	protected Resource resolveResourceInternal(@Nullable HttpServletRequest request, String requestPath, List<? extends Resource> locations, ResourceResolverChain chain) {
		
		Resource resolved = chain.resolveResource(request, requestPath, locations);
		if (resolved != null) {
			return resolved;
		}
		
		VersionStrategy versionStrategy = getStrategyForPath(requestPath);
		if (versionStrategy == null) {
			return null;
		}
		
		String candidateVersion = versionStrategy.extractVersion(requestPath);
		if (!StringUtils.hasLength(candidateVersion)) {
			return null;
		}
		
		String simplePath = versionStrategy.removeVersion(requestPath, candidateVersion);
		Resource baseResource = chain.resolveResource(request, simplePath, locations);
		if (baseResource == null) {
			return null;
		}
		
		String actualVersion = versionStrategy.getResourceVersion(baseResource);
		if (candidateVersion.equals(actualVersion)) {
			return new MyFileNameVersionedResource(baseResource, candidateVersion);
		} else {
			if (logger.isTraceEnabled()) {
				logger.trace("Found resource for \"" + requestPath + "\", but version [" + candidateVersion + "] does not match");
			}
			return null;
		}
	}
	
	private class MyFileNameVersionedResource extends AbstractResource implements HttpResource {
		
		private final Resource original;
		
		private final String version;
		
		public MyFileNameVersionedResource(Resource original, String version) {
			this.original = original;
			this.version = version;
		}
		
		@Override
		public boolean exists() {
			return this.original.exists();
		}
		
		@Override
		public boolean isReadable() {
			return this.original.isReadable();
		}
		
		@Override
		public boolean isOpen() {
			return this.original.isOpen();
		}
		
		@Override
		public boolean isFile() {
			return this.original.isFile();
		}
		
		@Override
		public URL getURL() throws IOException {
			return this.original.getURL();
		}
		
		@Override
		public URI getURI() throws IOException {
			return this.original.getURI();
		}
		
		@Override
		public File getFile() throws IOException {
			return this.original.getFile();
		}
		
		@Override
		@Nullable
		public String getFilename() {
			return this.original.getFilename();
		}
		
		@Override
		public long contentLength() throws IOException {
			return this.original.contentLength();
		}
		
		@Override
		public long lastModified() throws IOException {
			return this.original.lastModified();
		}
		
		@Override
		public Resource createRelative(String relativePath) throws IOException {
			return this.original.createRelative(relativePath);
		}
		
		@Override
		public String getDescription() {
			return this.original.getDescription();
		}
		
		@Override
		public InputStream getInputStream() throws IOException {
			return this.original.getInputStream();
		}
		
		@Override
		public HttpHeaders getResponseHeaders() {
			HttpHeaders headers = (this.original instanceof HttpResource ? ((HttpResource) this.original).getResponseHeaders() : new HttpHeaders());
//			headers.setETag("\"" + this.version + "\""); // 不添加ETag头
			return headers;
		}
	}
}
