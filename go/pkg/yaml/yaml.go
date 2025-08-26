package yaml

import (
	"os"
	"reflect"

	"gopkg.in/yaml.v3"
)

func Parse(filename string) (any, error) {
	var document any
	content, err := os.ReadFile(filename)
	if err != nil {
		return nil, err
	}

	err = yaml.Unmarshal(content, &document)
	return document, err
}

func Depth(object any) int {
	if object == nil {
		return 0
	}

	value := reflect.ValueOf(object)

	switch value.Kind() {
	case reflect.Map:
		maxDepth := 0
		for _, key := range value.MapKeys() {
			depth := Depth(value.MapIndex(key).Interface())
			if depth > maxDepth {
				maxDepth = depth
			}
		}
		return maxDepth + 1
	case reflect.Slice:
		maxDepth := 0
		for i := 0; i < value.Len(); i++ {
			depth := Depth(value.Index(i).Interface())
			if depth > maxDepth {
				maxDepth = depth
			}
		}
		return maxDepth + 1
	default:
		return 0
	}
}

func Exists(object any, ids ...any) bool {
	if len(ids) == 0 {
		return true
	} else if object == nil {
		return false
	}

	value := reflect.ValueOf(object)

	switch value.Kind() {
	case reflect.Map:
		idxValue := reflect.ValueOf(ids[0])
		idxElement := value.MapIndex(idxValue)
		if !idxElement.IsValid() {
			return false
		}
		return Exists(idxElement.Interface(), ids[1:]...)
	case reflect.Slice:
		idxValue := ids[0].(int)
		if idxValue >= value.Len() {
			return false
		}
		return Exists(value.Index(idxValue).Interface(), ids[1:]...)
	default:
		return false
	}
}

func Get(object any, ids ...any) any {
	if object == nil || len(ids) == 0 {
		return object
	}

	value := reflect.ValueOf(object)

	switch value.Kind() {
	case reflect.Map:
		idxValue := reflect.ValueOf(ids[0])
		return Get(value.MapIndex(idxValue).Interface(), ids[1:]...)
	case reflect.Slice:
		idxValue := ids[0].(int)
		return Get(value.Index(idxValue).Interface(), ids[1:]...)
	default:
		return nil
	}
}
