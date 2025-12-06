parts = IN.split("\n\n")
ranges = parts[0].split("\n")
ingredients = map(int, parts[1].split("\n"))

R = []

for se in ranges:
    s, e = map(int, se.split("-"))
    R.append([s,e])

ans=0
for ing in ingredients:
    for [s,e] in R:
        if s<= ing <= e:
            ans+=1
            break
print(ans)

# part 2
R=[]
ans=0
for se in ranges:
    s, e = map(int, se.split("-"))
    
    overlap_idx = []
    
    for i, r in enumerate(R):
        [s1, e1] = r
        if not (e1 < s or e < s1):
            overlap_idx.append(i)
            
    handled = False
    if overlap_idx:
        min_s, max_e = s, e
        for oi in overlap_idx:
            [s1, e1] = R[oi]
            min_s = min(min_s, s1)
            max_e = max(max_e, e1)
        for oi in overlap_idx[::-1]:
            R.pop(oi)
        R.append([min_s, max_e])
    else:
        R.append([s,e])

for r in R:
    [s,e] = r
    ans += (e-s+1)
print(ans)
        
  

