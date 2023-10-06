apiVersion: v1
kind: Pod
metadata:
  labels:
    product: datascience
<% try { %>
<% build_label=(build_label) %>
<% } catch(Exception e) { %>
<% build_label='default' %>
<% } %>
    app: ${build_label}
    buildnode: 'true'
spec:
  serviceAccountName: jenkins
  containers:
    - name: node
<% try { %>
<% node_version=(node_version) %>
<% } catch(Exception e) { %>
<% node_version='fermium' %>
<% } %>
<% try { %>
<% node_image_repository=(node_image_repository) %>
<% } catch(Exception e) { %>
<% node_image_repository='node' %>
<% } %>
      image: ${node_image_repository}:${node_version}
      imagePullPolicy: IfNotPresent
      command:
        - cat
      tty: true
<% try { %>
<% items=(image_dependencies) %>
<% } catch(Exception e) { %>
<% items=[] } %>
<% for (item in items) { %>
<%= item %>
<% } %>
  volumes:
    - hostPath:
        path: /var/run/docker.sock
        type: ''
      name: dockersock
