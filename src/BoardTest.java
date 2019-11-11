
public class BoardTest {
    final static int N = 8;
    static int[][] board = new int[N][N];;
    int count = 0;
    int countAll = 0;
    //马儿路线
    static int[] dx ={ -1, -2, -2, -1, 1, 2, 2, 1 };
    static int[] dy ={ 2, 1, -1, -2, -2, -1, 1, 2 };
    public static void main(String[] args) {
        BoardTest t = new BoardTest();
        int i, j;
        //初始化棋盘
        for(i=0; i<8; i++){
            for(j=0; j<8; j++){
                board[i][j] = 0;
            }
        }
        //第一步不用比较，赋值第一步
        //x,y  0-7
        int x = 6;
        int y = 0;
        board[x][y] = 1;
        t. move(x, y, 2);

    }

    void move(int x ,int y ,int count){
        int mx ,my;
        HorseNode[]  h = new HorseNode[N];

        if(count >N*N){
            //遍历打印结果
            for(int i=0; i<N; i++){
                for(int j=0; j<N; j++){
                    System.out.printf("%3d", board[i][j]);
                }
                System.out.println();
            }
            System.out.println(countAll);
            //结束
            System.exit(0);
        }

        for(int i = 0; i<N;i++){
            //循环周围可以跳的位置的信息放入数组
            mx = x + dx[i];
            my = y + dy[i];
//          if(mx<0||my<0||mx>=N||my>=N){
//              System.out.println("跳过"+mx+","+my);
//              continue;
//          }

            HorseNode hor = new HorseNode();
            h[i] = hor;
            h[i].x = mx;
            h[i].y = my;
            h[i].waysOutNum = getWays(mx,my);
            System.out.println(h[i]);

        }

        //排序
        sort(h);

//      方式一
//       int a;
//       for(a=0;h[a].waysOutNum<0;++a);
//          for(;a<N;++a){
//              mx=h[a].x;
//              my=h[a].y;
//              board[mx][my]=count;
//              move(mx,my,count+1);
//              board[mx][my]=0;
//       }
        //方式二
        for(int i = 0; i<h.length;i++){
            //注释部分是方式一完整写法,或许下面更好理解
//          if(h[i].waysOutNum<0){
//              continue;
//          }
//               mx=h[i].x;
//               my=h[i].y;
//               board[mx][my]=count;
//           move(mx,my,count+1);
//           board[mx][my]=0;
            if(h[i].waysOutNum>=0){
                mx=h[i].x;
                my=h[i].y;
                board[mx][my]=count;
                move(mx,my,count+1);
                //回溯时当前棋盘状态,下一步已经没地方可跳,下面有图
                //for(int n=0; n<N; n++){
                //     for(int m=0; m<N; m++){
                //         System.out.printf("%3d", board[n][m]);
                //     }
                //     System.out.println();
                // }
                //System.exit(0);

                 /*
                                        很重要
                    board[mx][my]=0;
                    回溯用法
                    如果move(mx,my,count+1);马跳了之后后发现没有位置可跳.
                    本次的for循环不会在循环,
                    而是把他们都复原
                  */
                //回溯
                board[mx][my]=0;
                //记录总执行次数
                countAll++;
            }
        }

    }

    //根据二次可跳位置次数进行排序
    void sort(HorseNode[] arr ){
        //for(int b = 0;b<arr.length;b++){
        //  System.out.print(arr[b].x+","+arr[b].y+"--/"+arr[b].waysOutNum+"   ");
        //}
        int i;
        HorseNode h;
        for(int s = 0;s<arr.length;s++){
            for(i = 0;i<s;i++){
                if(arr[i].waysOutNum>arr[i+1].waysOutNum){
                    h = arr[i];
                    arr[i]=arr[i+1];
                    arr[i+1] = h;
                }
            }
        }


    }

    int getWays(int x, int y){

        int mx ,my,c =0;

//      System.out.println(x+","+y);
        //超出边界,或者已占用      x y   0-7
        if(x<0||y<0||x>=N||y>=N||board[x][y]>0){
            return -1;
        }

        for(int i = 0;i<N;i++){
            mx = x + dx[i];
            my = y + dy[i];
            if(mx<0||my<0||mx>=N||my>=N){
//              System.out.println("跳过"+mx+","+my);
                continue;
            }
            if(board[mx][my]==0){
                //board[mx][my]==0  代表还没跳过
                c++;
            }
        }
//      System.out.println(x+","+y+"---"+c);
        return c;
    }
    class HorseNode {
        int x;
        int y;
        int waysOutNum;
    }
}
