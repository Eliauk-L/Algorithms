/**
 * @author Jay
 * @description
 * @date 2026/9/7
 */

public class LinkedList {
    public static class ListNode {
        int val;
        ListNode next;
        ListNode(int x){
            val = x;
            next = null;
        }
        ListNode(int x, ListNode n){
            val = x;
            next = n;
        }
    }

    public static void main(String[] args) {
        ListNode a = new ListNode(1);
        ListNode b = new ListNode(2,a);
        ListNode c = new ListNode(3,b);
        ListNode head = new ListNode(4,c);

        ListNode p = head;
        while(p != null){
            System.out.println(p.val);
            p = p.next;
        }

        head = reverseLinkedList(head);

        System.out.println("============");
        p = head;
        while(p != null){
            System.out.println(p.val);
            p = p.next;
        }
    }

    // 链表翻转--迭代
    public static ListNode reverseLinkedList(ListNode head){
        ListNode prev = null;
        ListNode p = head;
        while(p != null){
            ListNode next = p.next;
            p.next = prev;
            prev = p;
            p = next;
        }
        return prev;
    }

    //链表翻转--递归
    // 以链表1->2->3->4->5举例
    public static ListNode reverseLinkedList2(ListNode head){
        if(head == null || head.next == null){
             /*
                直到当前节点的下一个节点为空时返回当前节点
                由于5没有下一个节点了，所以此处返回节点5
             */
            return head;
        }
        ListNode newHead = reverseLinkedList2(head.next);
         /*
            第一轮出栈，head为5，head.next为空，返回5
            第二轮出栈，head为4，head.next为5，执行head.next.next=head也就是5.next=4，
                      把当前节点的子节点的子节点指向当前节点
                      此时链表为1->2->3->4<->5，由于4与5互相指向，所以此处要断开4.next=null
                      此时链表为1->2->3->4<-5
                      返回节点5
            第三轮出栈，head为3，head.next为4，执行head.next.next=head也就是4.next=3，
                      此时链表为1->2->3<->4<-5，由于3与4互相指向，所以此处要断开3.next=null
                      此时链表为1->2->3<-4<-5
                      返回节点5
            第四轮出栈，head为2，head.next为3，执行head.next.next=head也就是3.next=2，
                      此时链表为1->2<->3<-4<-5，由于2与3互相指向，所以此处要断开2.next=null
                      此时链表为1->2<-3<-4<-5
                      返回节点5
            第五轮出栈，head为1，head.next为2，执行head.next.next=head也就是2.next=1，
                      此时链表为1<->2<-3<-4<-5，由于1与2互相指向，所以此处要断开1.next=null
                      此时链表为1<-2<-3<-4<-5
                      返回节点5
            出栈完成，最终头节点5->4->3->2->1
         */
        head.next.next = head;
        head.next = null;
        return newHead;
    }

    //环形链表判断
    public static boolean hasCycle(ListNode head){
        if(head == null || head.next == null) return false;
        ListNode slow = head;
        ListNode fast = head.next;
        while(slow != fast){
            if(fast == null || fast.next == null)
                return false;
            slow = slow.next;
            fast = fast.next.next;
        }
        return true;
    }

    //找出环形链表入口
    // a+(n+1)b+nc = 2(a+b) ==> a = c + (n-1)(b+c)
    public static ListNode whereCycle(ListNode head){
        ListNode slow = head;
        ListNode fast = head;
        while(fast != null){
            slow = slow.next;
            if(fast.next != null){
                fast = fast.next.next;
            }else{
                return null;
            }

            if(fast == slow){
                ListNode ptr = head;
                while(ptr != slow){
                    ptr = ptr.next;
                    slow = slow.next;
                }
                return ptr;
            }
        }
        return null;
    }

    //删除倒数第n个链表节点
    public static ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dummy = new ListNode(-1,head);
        ListNode fast = dummy;
        ListNode slow = head;
        while(n-->0){
            fast = fast.next;
        }
        while(fast!=null){
            fast = fast.next;
            slow = slow.next;
        }
        slow.next = slow.next.next;
        return dummy.next;
    }

    //两两交换节点--递归
    public ListNode swapPairs(ListNode head) {
        // 递归在只有一个节点或者没有节点时结束
        if(head == null || head.next == null){
            return head;
        }
        ListNode newHead = head.next;
        head.next = swapPairs(newHead.next);
        newHead.next = head;
        return newHead;
    }

    //两两交换节点--迭代
    public ListNode swapPairs2(ListNode head) {
        ListNode dummy = new ListNode(-1,head);// 哑节点表示需要交换的两个节点的前一节点
        ListNode cur = dummy;
        while(cur.next != null && cur.next.next != null){
            //cur -> node1 -> node2 ==> cur -> node2 -> node1
            ListNode node1 = cur.next;
            ListNode node2 = cur.next.next;
            cur.next = node2;
            node1.next = node2.next;
            node2.next = node1;
            cur = node1;
        }
        return dummy.next;
    }

    //归并排序链表
    public ListNode sortList(ListNode head){
        return sortList(head,null);
    }

    public ListNode sortList(ListNode head,ListNode tail){
        if(head == null) return head;
        if(head.next == tail){
            head.next = null;
            return head;
        }
        // 找到中间节点
        ListNode slow = head,fast = head;
        while(fast != tail){
            slow = slow.next;
            fast = fast.next;
            if(fast != tail)
                fast = fast.next;
        }

        ListNode mid = slow;
        ListNode list1 = sortList(head,mid);
        ListNode list2 = sortList(mid,tail);
        ListNode sorted = merge(list1,list2);
        return sorted;
    }

    //合并有序列表
    public ListNode merge(ListNode list1, ListNode list2){
        ListNode dummy = new ListNode(-1);
        ListNode p = dummy;
        while(list1 != null && list2 != null){
            if(list1.val < list2.val){
                p.next = list1;
                list1 = list1.next;
            }else{
                p.next = list2;
                list2 = list2.next;
            }
            p = p.next;
        }
        p.next = (list1 == null) ? list2 : list1;
        return dummy.next;
    }

    public ListNode merge2(ListNode list1, ListNode list2){
        if(list1 == null)
            return list2;
        if(list2 == null)
            return list1;
        if(list1.val < list2.val){
            list1.next = merge2(list1.next,list2);
            return list1;
        }else{
            list2.next = merge2(list1,list2.next);
            return list2;
        }
    }

    //合并k个有序链表
    public ListNode mergeKLists(ListNode[] lists) {
        return merge(lists,0,lists.length-1);
    }
    public ListNode merge(ListNode[] lists,int l, int r){
        if(l == r)
            return lists[l];
        if(l > r)
            return null;
        int mid = (l + r) >> 1;
        return mergeTwoLists(merge(lists,l,mid),merge(lists,mid+1,r));
    }

    public ListNode mergeTwoLists(ListNode a,ListNode b){
        if(a == null || b == null)
            return a==null ? b : a;

        ListNode head = new ListNode(0);
        ListNode tail = head;
        while(a != null && b != null){
            if(a.val < b.val){
                tail.next = a;
                a = a.next;
            }else{
                tail.next = b;
                b = b.next;
            }
            tail = tail.next;
        }

        tail.next = (a == null) ? b : a;
        return head.next;
    }
}
