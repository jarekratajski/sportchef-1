/*
 * SportChef – Sports Competition Management Software
 * Copyright (C) 2016 Marcus Fihlon
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package ch.sportchef.business.admin.boundary;

import ch.sportchef.business.configuration.control.ConfigurationService;
import ch.sportchef.business.configuration.entity.Configuration;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static javax.ws.rs.core.Response.Status.FORBIDDEN;
import static javax.ws.rs.core.Response.Status.OK;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

public class AdminResourceTest {

    @Test
    public void getAdminPageOK() throws IOException {
        // arrange
        final Map<Object, Object> properties = new HashMap<>();
        properties.put("admin.password", "correct_password");
        final Configuration configuration = new Configuration(properties);
        final ConfigurationService configurationServiceMock = mock(ConfigurationService.class);
        when(configurationServiceMock.getConfiguration())
                .thenReturn(configuration);
        final AdminResource adminResource = new AdminResource(configurationServiceMock);

        // act
        final Response response = adminResource.getAdminPage("correct_password");

        //assert
        assertThat(response.getStatus(), is(OK.getStatusCode()));
        verify(configurationServiceMock, times(1)).getConfiguration();
    }

    @Test
    public void getAdminPageForbidden() throws IOException {
        // arrange
        final Map<Object, Object> properties = new HashMap<>();
        properties.put("admin.password", "correct_password");
        final Configuration configuration = new Configuration(properties);
        final ConfigurationService configurationServiceMock = mock(ConfigurationService.class);
        when(configurationServiceMock.getConfiguration())
                .thenReturn(configuration);
        final AdminResource adminResource = new AdminResource(configurationServiceMock);

        // act
        final Response response = adminResource.getAdminPage("wrong_password");

        //assert
        assertThat(response.getStatus(), is(FORBIDDEN.getStatusCode()));
        verify(configurationServiceMock, times(1)).getConfiguration();
    }

}
