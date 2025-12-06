from functools import reduce

parts = IN.split("\n")
numss = parts[:4]
ops = parts[4]

arr = []
for nums in numss:
  ns = list(map(int, nums.split()))
  arr.append(ns)

ans=0

for i, op in enumerate(ops.split()):
  if op == "+":
    tmp = 0
    for a in arr:
      tmp+=a[i]
  else:
    tmp = 1
    for a in arr:
      tmp*=a[i]
  ans+=tmp
print(ans)    
  
# part 2

i = 0
ans = 0
while i < len(ops):
  l = i
  i+=1
  while i < len(ops)-1 and ops[i+1] == ' ':
    i+=1
  r = i
  i+=1
  if i == len(ops):
    r+=1
  
  cur_nums = []
  for j in range(l, r):
    tmp=0
    for nums in numss:
      if nums[j] == ' ' :
        continue
      tmp*=10
      tmp+=int(nums[j])
    cur_nums.append(tmp)
  if ops[l] == "+":
    ans += reduce(lambda a,b: a+b, cur_nums, 0)
  else:
    ans += reduce(lambda a,b: a*b, cur_nums, 1)
print(ans)
      
    
