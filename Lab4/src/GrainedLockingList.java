public class GrainedLockingList {

    private GrainNode list = new GrainNode(new Object() {
        public String toString() {
            return "Wartownik";
        }
    }, null);

    public boolean add(Object obj) {
        return list.add(obj);
    }

    public boolean add(Object obj, int milis) {
        return list.add(obj, milis);
    }

    public boolean remove(Object obj) {
        return list.remove(obj);
    }

    public boolean contains(Object obj) {
        return list.contains(obj);
    }

    public boolean contains(Object obj, int milis) {
        return list.contains(obj, milis);
    }

    public void print() {
        GrainNode current = list.next;
        while (current != null) {
            System.out.print(current.getValue() + " ");
            current = current.next;
        }
        System.out.println();
    }

}