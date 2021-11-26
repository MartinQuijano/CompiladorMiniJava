class Init{

    static void main() {
        int x = 5;
        for(int y = 0; y < x; y++)
            for(int z = 10; z > x; z--)
                debugPrint(y+z);
    }

}
