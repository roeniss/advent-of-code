from collections import defaultdict

poses = IN.split("\n")
poses = [list(map(int, pos.split(","))) for pos in poses]

dist = []

max_turn = 1000
cur_turn = 1

def get_dist(a, b):
  return (a[0] - b[0])**2 + (a[1] - b[1])**2 + (a[2] - b[2])**2

for i in range(0, len(poses)-1):
  for j in range(i+1, len(poses)):
    dist.append([i, j, get_dist(poses[i], poses[j])])

dist = sorted(dist, key = lambda x: x[2])    
arr = [i for i in range(len(poses))]

def find_root(arr, i):
  if i == arr[i]:
    return i
  else: 
    n = find_root(arr, arr[i])
    arr[i] = n
    return n

def union(arr, i, j, prev):
  if i > j: i, j = j, i
  ii = find_root(arr, i)
  jj = find_root(arr, j)
    
  if arr[ii] != arr[jj]:
    arr[ii] = arr[jj]
    return poses[i][0] * poses[j][0]
  else:
    return prev



ans = 0
while True:
  nxt = dist.pop(0)
  i, j = nxt[0], nxt[1]
  ans = union(arr, i, j, ans)
  if len(set(arr)) == 1:
    break

# d = defaultdict(lambda: 0)
# for i in range(len(poses)):
#   d[find_root(arr, i)]+=1
  
# res = sorted(d.values(), reverse=True)
# print(res)
# print(res[0] * res[1] * res[2])
  

print(ans)