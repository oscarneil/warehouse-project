n = int(input())
for l in range(n):
    v = input()
    try:
        
        l = 1 / float(v)
        print(l)
    except:
        print("inf")