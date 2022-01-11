"""

LeetCode 588. Design In-Memory File System

# http://bookshadow.com/weblog/2017/05/21/leetcode-design-in-memory-file-system/

Design an in-memory file system to simulate the following functions:

ls: Given a path in string format. If it is a file path, return a list that only contains this file's name. If it is a directory path, return the list of file and directory names in this directory. Your output (file and directory names together) should in lexicographic order.

mkdir: Given a directory path that does not exist, you should make a new directory according to the path. If the middle directories in the path don't exist either, you should create them as well. This function has void return type.

addContentToFile: Given a file path and file content in string format. If the file doesn't exist, you need to create that file containing given content. If the file already exists, you need to append given content to original content. This function has void return type.

readContentFromFile: Given a file path, return its content in string format.

Example:

Input: 
["FileSystem","ls","mkdir","addContentToFile","ls","readContentFromFile"]
[[],["/"],["/a/b/c"],["/a/b/c/d","hello"],["/"],["/a/b/c/d"]]
Output:
[null,[],null,null,["a"],"hello"]
Explanation:
filesystem
Note:

You can assume all file or directory paths are absolute paths which begin with / and do not end with / except that the path is just "/".
You can assume that all operations will be passed valid parameters and users will not attempt to retrieve file content or list a directory or file that does not exist.
You can assume that all directory names and file names only contain lower-case letters, and same names won't exist in the same directory.

"""

# V0

# V1
# IDEA : Tree + Dict
# http://bookshadow.com/weblog/2017/05/21/leetcode-design-in-memory-file-system/
class FileSystem(object):

    def __init__(self):
        self.root = {'dirs' : {}, 'files': {}}

    def ls(self, path):
        """
        :type path: str
        :rtype: List[str]
        """
        node, type = self.getExistedNode(path)
        if type == 'dir': return sorted(node['dirs'].keys() + node['files'].keys())
        return [path.split('/')[-1]]

    def mkdir(self, path):
        """
        :type path: str
        :rtype: void
        """
        node = self.root
        for dir in filter(len, path.split('/')):
            if dir not in node['dirs']: node['dirs'][dir] = {'dirs' : {}, 'files': {}}
            node = node['dirs'][dir]

    def addContentToFile(self, filePath, content):
        """
        :type filePath: str
        :type content: str
        :rtype: void
        """
        dirs = filePath.split('/')
        path, file = '/'.join(dirs[:-1]), dirs[-1]
        self.mkdir(path)
        node, type = self.getExistedNode(path)
        if file not in node['files']: node['files'][file] = ''
        node['files'][file] += content

    def readContentFromFile(self, filePath):
        """
        :type filePath: str
        :rtype: str
        """
        dirs = filePath.split('/')
        path, file = '/'.join(dirs[:-1]), dirs[-1]
        node, type = self.getExistedNode(path)
        return node['files'][file]
        
    def getExistedNode(self, path):
        """
        :type path: str
        :rtype: str
        """
        node = self.root
        for dir in filter(len, path.split('/')):
            if dir in node['dirs']: node = node['dirs'][dir]
            else: return node, 'file'
        return node, 'dir'

# V1'
# https://blog.csdn.net/magicbean2/article/details/78950619

# V1''
# https://blog.csdn.net/Changxing_J/article/details/110877794

# V1'''
# https://leetcode.jp/leetcode-588-design-in-memory-file-system-%E8%A7%A3%E9%A2%98%E6%80%9D%E8%B7%AF%E5%88%86%E6%9E%90/
# JAVA
# Node root = new Node();
# public FileSystem() {
# }
# public List<String> ls(String path) {
#     String[] dirs = path.split("/");
#     Node node=root;
#     for(String dir : dirs){
#         if("".equals(dir)) continue;
#         node=node.fileList.get(dir);
#     }
#     List<String> res = new ArrayList<>(node.fileList.keySet());
#     return res;
# }
# public void mkdir(String path) {
#     String[] dirs = path.split("/");
#     Node node=root;
#     for(String dir : dirs){
#         if("".equals(dir)) continue;
#         Node child=node.fileList.get(dir);
#         if(child==null){
#             child=new Node();
#             node.fileList.put(dir, child);
#         }
#         node = child;
#     }
# }
# public void addContentToFile(String filePath, String content) {
#     String[] dirs = filePath.split("/");
#     Node node=root;
#     for(int i=0;i<dirs.length;i++){
#         String dir=dirs[i];
#         if("".equals(dir)) continue;
#         Node child=node.fileList.get(dir);
#         if(child==null){
#             child=new Node();
#             node.fileList.put(dir, child);
#         }
#         if(i==dirs.length-1){
#             child.text.append(content);
#             child.fileList.put(dir,null);
#         }
#         node = child;
#     }
# }
# public String readContentFromFile(String filePath) {
#     String[] dirs = filePath.split("/");
#     Node node=root;
#     for(String dir : dirs){
#         if("".equals(dir)) continue;
#         node=node.fileList.get(dir);
#     }
#     return node.text.toString();
# }
#    
# class Node{
#     Map<String, Node> fileList = new TreeMap<>();
#     StringBuilder text= new StringBuilder();
# }

# V1''''
# https://www.cnblogs.com/grandyang/p/6944331.html

# V2