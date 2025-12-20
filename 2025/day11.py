d = {}
lines = IN.split("\n")
for line in lines:
  frm = line.split(": ")[0]
  toList = line.split(": ")[1].split(" ")
  d[frm] = toList

vis = set()

mem_for_none = {}
mem_for_dac = {}
mem_for_fft = {}
mem_for_dac_fft = {}

def bt(cur, d, vis):
  if "dac" in vis and "fft" in vis:
    if cur in mem_for_dac_fft:
      return mem_for_dac_fft[cur]
  elif "dac" in vis:
    if cur in mem_for_dac:
      return mem_for_dac[cur]
  elif "fft" in vis:
    if cur in mem_for_fft:
      return mem_for_fft[cur]
  else:
    if cur in mem_for_none:
      return mem_for_none[cur]

  ans = 0 
  for nxt in d[cur]:
    if nxt == "out":
      # ans+= 1 # part 1
      if "dac" in vis and "fft" in vis: # part 2
        ans+= 1
      else:
        pass
    elif nxt in vis:
      continue
    else:
      vis.add(nxt)
      ans+=bt(nxt, d, vis)
      vis.remove(nxt)
  
  if "dac" in vis and "fft" in vis:
    mem_for_dac_fft[cur] = ans
  elif "dac" in vis:
    mem_for_dac[cur] = ans
  elif "fft" in vis:
    mem_for_fft[cur] = ans
  else:
    mem_for_none[cur] = ans
  
  return ans


ans=bt("svr", d, vis)
print(ans)