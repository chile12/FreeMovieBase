<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
 
<tiles:insertDefinition name="defaultTemplate">
    <tiles:putAttribute name="body">
                
         <p>Hello! This is the default welcome page for a Spring Web MVC project.</p>
         <p><i>To display a different welcome page for this project, modify</i>
             <tt>index.jsp</tt> <i>, or create your own welcome page then change
                 the redirection in</i> <tt>redirect.jsp</tt> <i>to point to the new
                 welcome page and also update the welcome-file setting in</i>
             <tt>web.xml</tt>.</p>
	</tiles:putAttribute>
</tiles:insertDefinition>