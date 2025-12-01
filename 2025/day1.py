INPUT = """R1
R25"""

ans = 0
cur = 50
for line in INPUT.split("\n"):
    t = int(line[1:])
    # print(line, cur)
    if line[0] == "R":
        while t > 0:
            t -= 1
            cur += 1
            cur %= 100
            if cur == 0:
                ans += 1
                # print("found R")
    else:
        while t > 0:
            t -= 1
            cur -= 1
            if cur < 0:
                cur += 100
            if cur == 0:
                ans += 1
                # print("found L")
    # print(cur)

print(ans)