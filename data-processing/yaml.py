from pprint import pprint
import yaml
from flatten_dict import flatten
from flatten_dict.reducer import make_reducer

contents = []
content_dicts = [yaml.load(content, Loader=yaml.SafeLoader) for content in contents]


def flatten_list_of_dicts(ld):
    root = {}
    for d in ld:
        for k, v in d.items():
            if k in root:
                root[k].append(v)
            else:
                root[k] = [v]
    return root


def flatten_all(d):
    flattened = False
    while not flattened:
        d = flatten(d, reducer=make_reducer('.'))
        keys = list(d.keys())
        nested_found = False
        for k in keys:
            if type(d[k]) is list:
                for i in range(len(d[k])):
                    d[f"{k}[{i}]"] = d[k][i]
                del d[k]
                nested_found = True
        flattened = not nested_found
    return flatten(d, reducer=make_reducer('.'))


pprint(flatten_list_of_dicts([flatten_all(d) for d in content_dicts]))