public class RedBlackTree {
    private Node root;

    public RedBlackTree(){
        this.root = null;
    }
    // MÉTODOS PRINCIPAIS:

    public boolean isRedBlack(Node no){
        if(no == null) return true;
        int left = blackHeight(no.left);
        int right = blackHeight(no.right);
        if(left != right) return false;
        return isRedBlack(no.left) && isRedBlack(no.right);
    }

    public int blackHeight(Node no){
        if(no == null) return 1;
        int left = blackHeight(no.left);
        int right = blackHeight(no.right);
        return Math.max(left, right) + (no.isRed ? 0 : 1);
    }

    public void insert(int v){
        Node newNode = insert(this.root, v, null);
        if(newNode != null) this.root = newNode;
        this.root.isRed = false;
    }

    private Node insert(Node n, int v, Node parent){
        if(n == null){ 
            Node newNode = new Node(v);
            newNode.parent = parent;
            fixInsert(newNode);
            return newNode;
        }
        if(v < n.value) n.left = insert(n.left, v, n);
        else if(v > n.value) n.right = insert(n.right, v, n);
        else return n;
        return n;
    }

    private void fixInsert(Node no){
        if(no.parent == null) case1(no);
        else if(!no.parent.isRed) case2(no);
        else if(uncle(no) != null && uncle(no).isRed) case3(no);
        else case4(no);
    }

    private void case1(Node no){
        no.isRed = false;
    }

    private void case2(Node no){
        return;
    }

    private void case3(Node no){
        no.parent.isRed = false;
        uncle(no).isRed = false;
        Node grandpa = grandparent(no);
        grandpa.isRed = true;
        fixInsert(grandpa);
    }

    private void case4(Node no){
        Node grandpa = grandparent(no);
        if(no == no.parent.right && no.parent == grandpa.left){
            rotateLeft(no.parent);
            no = no.left;
        } else if(no == no.parent.left && no.parent == grandpa.right){
            rotateRight(no.parent);
            no = no.right;
        }
        case5(no);
    }

    private void case5(Node no){
        no.parent.isRed = false;
        Node grandpa = grandparent(no);
        grandpa.isRed = true;
        if(no == no.parent.left && no.parent == grandpa.left) rotateRight(grandpa);
        else rotateLeft(grandpa);
    }

    private Node grandparent(Node no){
        return (no != null && no.parent != null) ? no.parent.parent : null;
    }

    private Node uncle(Node no){
        Node grandpa = grandparent(no);
        if(grandpa == null) return null;
        return (no.parent == grandpa.left) ? grandpa.right : grandpa.left;
    }

    public Node search(int v){
        return search(this.root, v);
    }

    private Node search(Node n, int v){
        if(n == null) return null;
        if(n.value == v) return n;
        else if(v < n.value) return search(n.left, v);
        else return search(n.right, v);
    }
    // MÉTODOS AUXILIARES:
    private int height(Node n){
        return (n == null) ? -1 : n.height; // Retorna o atributo da altura do nó ou -1 se o nó for nulo;
    }

    private int max(int a, int b){
        return (a > b) ? a : b; // Retorna o maior valor. Simples.
    }

    private Node rotateLeft(Node n){
        Node x = n.right;
        Node parent = n.parent;
        Node t2 = x.left;
        
        x.left = n; // rotaciona aqui
        n.parent = x;
        n.right = t2; // e aqui

        if(t2 != null) t2.parent = n;
        if(parent == null) this.root = x;
        else if(parent.right == n) parent.right = x;
        else parent.left = x;
        x.parent = parent;

        n.height = 1 + max(height(n.left), height(n.right)); // atualiza a altura aqui
        x.height = 1 + max(height(x.left), height(x.right)); // e aqui
        return x;
    }

    private Node rotateRight(Node n){
        Node x = n.left;
        Node parent = n.parent;
        Node t2 = x.right;
        x.right = n; // rotaciona aqui
        n.parent = x;
        n.left = t2; // e aqui

        if(t2 != null) t2.parent = n;
        if(parent == null) this.root = x;
        else if(parent.right == n) parent.right = x;
        else parent.left = x;
        x.parent = parent;

        n.height = 1 + max(height(n.left), height(n.right)); // atualiza a altura aqui
        x.height = 1 + max(height(x.left), height(x.right)); // e aqui
        return x;
    }
}
class Node{
    int value;
    Node left, right, parent;
    int height;
    boolean isRed;

    Node(int v){
        this.value = v;
        this.height = 0;
        this.isRed = true;
    }
}