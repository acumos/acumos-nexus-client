/*-
 * ===============LICENSE_START=======================================================
 * Acumos
 * ===================================================================================
 * Copyright (C) 2017 AT&T Intellectual Property & Tech Mahindra. All rights reserved.
 * ===================================================================================
 * This Acumos software file is distributed by AT&T and Tech Mahindra
 * under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * This file is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ===============LICENSE_END=========================================================
 */

package org.acumos.nexus.client.test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URISyntaxException;

import org.acumos.nexus.client.NexusArtifactClient;
import org.acumos.nexus.client.RepositoryLocation;
import org.acumos.nexus.client.data.UploadArtifactInfo;
import org.codehaus.plexus.util.FileUtils;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Realistic tests require a functioning remote server. They are marked as Ignore here.
 */
public class NexusArtifactClientTest {
	
	private static Logger logger = LoggerFactory.getLogger(NexusArtifactClientTest.class);

	private final String REPO_URL = "http://mvnrepo.com:8081/repository/maven-central";
	private final String REPO_USER = "test";
	private final String REPO_PASS = "test";
	
	@Test
	public void testTransport() {
		final String groupId = "g";
		final String artifactId = "a";
		final String version = "v";
		final String packaging = "p";
		final String artifactMvnPath = "m";
		final long contentlength = 42L;
		UploadArtifactInfo uai = new UploadArtifactInfo(groupId, artifactId, version, packaging, artifactMvnPath,
				contentlength);
		Assert.assertEquals(groupId, uai.getGroupId());
		Assert.assertEquals(artifactId, uai.getArtifactId());
		Assert.assertEquals(version, uai.getVersion());
		Assert.assertEquals(packaging, uai.getPackaging());
		Assert.assertEquals(artifactMvnPath, uai.getArtifactMvnPath());
		Assert.assertEquals(contentlength, uai.getContentlength());
		uai.setGroupId(groupId);
		uai.setArtifactId(artifactId);
		uai.setVersion(version);
		uai.setPackaging(packaging);
		uai.setArtifactMvnPath(artifactMvnPath);
		uai.setContentlength(contentlength);
		Assert.assertEquals(groupId, uai.getGroupId());
		Assert.assertEquals(artifactId, uai.getArtifactId());
		Assert.assertEquals(version, uai.getVersion());
		Assert.assertEquals(packaging, uai.getPackaging());
		Assert.assertEquals(artifactMvnPath, uai.getArtifactMvnPath());
		Assert.assertEquals(contentlength, uai.getContentlength());
	}

	@Ignore
	@Test
	public void testGetArtifact() throws Exception {
		RepositoryLocation repositoryLocation = new RepositoryLocation();
		repositoryLocation.setId("0");
		repositoryLocation.setUrl(REPO_URL);
		repositoryLocation.setUsername(REPO_USER);
		repositoryLocation.setPassword(REPO_PASS);
		repositoryLocation.setProxy("");
		NexusArtifactClient artifactClient = new NexusArtifactClient(repositoryLocation);

		ByteArrayOutputStream outputStream = artifactClient
				.getArtifact("ch/qos/logback/logback-classic/1.1.11/logback-classic-1.1.11.jar");
		if (outputStream != null) {
			outputStream.close();
		}
	}

	@Ignore
	@Test
	public void testUploadArtifact() throws Exception {
		RepositoryLocation repositoryLocation = new RepositoryLocation();
		repositoryLocation.setId("1");
		repositoryLocation.setUrl(REPO_URL);
		repositoryLocation.setUsername(REPO_USER);
		repositoryLocation.setPassword(REPO_PASS);
		repositoryLocation.setProxy("");
		NexusArtifactClient artifactClient = new NexusArtifactClient(repositoryLocation);

		// Test Only - START
		// to create an Artifact.
		File tempFile = File.createTempFile("uploadArtifact", "txt");
		tempFile.deleteOnExit();
		String content = "put top secret";
		FileUtils.fileWrite(tempFile.getAbsolutePath(), content);
		FileInputStream fileInputStream = new FileInputStream(tempFile);
		// Test Only - END
		try {
			UploadArtifactInfo artifactInfo = artifactClient.uploadArtifact("com.model", "uploadArtifact",
					"1.0.0-SNAPSHOT", "txt", content.length(), fileInputStream);
			logger.debug("Uploaded Artifcta Referece: " + artifactInfo.getArtifactMvnPath());
		} finally {
			fileInputStream.close();
			tempFile.delete();
		}
	}

	@Ignore
	@Test
	public void testDeleteArtifact() throws URISyntaxException {
		RepositoryLocation repositoryLocation = new RepositoryLocation();
		repositoryLocation.setId("1");
		repositoryLocation.setUrl(REPO_URL);
		repositoryLocation.setUsername(REPO_USER);
		repositoryLocation.setPassword(REPO_PASS);
		repositoryLocation.setProxy("");
		NexusArtifactClient artifactClient = new NexusArtifactClient(repositoryLocation);
		artifactClient.deleteArtifact("ch/qos/logback/logback-classic/1.1.11/logback-classic-1.1.11.jar");
	}
}
