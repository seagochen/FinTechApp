import re
import os

from utils.TextHandler import trim_tailer_from_text


class RegularToken(object):

    def __init__(self, key: str, mapping: str, regular: str):
        self.key = key
        self.mapping = mapping
        self.regular = regular


class ArgsMappingToken(object):
    """
    The token used to store the parsed data, including the key of the key-value pair,
    the mapped key, and the regular matching rules
    """

    def __init__(self):
        self.tokens = []

    def search_token(self, key):
        """
        from token list, search the matched token
        """

        for t in self.tokens:
            if t.key == key:
                return t

        return None

    def use_args_regular_check(self, args: dict):
        """
        Regularly match the parameters, if the format is correct, the data of the parameter will be retained,
        if it is wrong, it will be discarded
        """

        final_dict = {}

        # first, verify keys from second variable are matches to first variable
        for key, val in args.items():
            token = self.search_token(key)

            if not token:
                continue

            # do regular check
            if re.match(token.regular, val) is not None:
                final_dict[token.mapping] = val
            else:
                final_dict[token.mapping] = None

        # return to caller
        return final_dict

    def clear_tokens(self):
        """
        cleaning up data caches
        """
        self.tokens.clear()

    def parsing_reg_file(self, rule_file: str, args: dict):
        """
        Parse the configuration file, and then verify the validity of the input parameters

        @Args:
        * [ruleFile] str, path of file
        * [args] dict, data contained with key and value

        @Returns:
        * [dict(str:obj)] dict type
        """

        if not os.path.exists(rule_file):
            raise OSError("file: {} not exists".format(rule_file))

        # parse the file
        for line in open(rule_file, "rt"):
            # trim tailor
            line = trim_tailer_from_text(line)

            # split token from spaces
            seps = line.split(' ')

            # check size
            if len(seps) != 3:
                raise OSError("regular line: {} is broken, [argument key] [mapping key] [regular expression]"
                              .format(line))

                # append regular token to list
            self.tokens.append(RegularToken(seps[0], seps[1], R"{}".format(seps[2])))

        # After parsing the file, first extract the dictionary consisting of {old key-regular match} from the file,
        # and then perform regular filtering on the parameters
        filtered_args = self.use_args_regular_check(args)

        return filtered_args

    def parsing_reg_rules(self, rule_list: list, args: dict):
        """
        Parse the configuration list, and then verify the validity of the input parameters

        the format of rule list:
        [argument key] [mapping key] [regular expression]

        @Args:
        * [ruleList] list
        * [args] dict, data contained with key and value

        @Returns:
        * [dict(str:obj)] dict type
        """

        # parse the file
        for line in rule_list:
            # split token from spaces
            seps = line.split(' ')

            # check size
            if len(seps) != 3:
                raise OSError("regular line: {} is broken, [argument key] [mapping key] [regular expression]"
                              .format(line))

            # append regular token to list
            self.tokens.append(RegularToken(seps[0], seps[1], seps[2]))

        # After parsing the file, first extract the dictionary consisting of {old key-regular match} from the file,
        # and then perform regular filtering on the parameters
        filtered_args = self.use_args_regular_check(args)

        return filtered_args


def apply_reg_rules(rule, args):
    """
    Regular matching rules can be read from a file or from a matching list
    """

    args_token = ArgsMappingToken()

    if isinstance(rule, str):  # read from file
        return args_token.parsing_reg_file(rule, args)

    if isinstance(rule, list):
        return args_token.parsing_reg_rules(rule, args)

    return None


if __name__ == "__main__":
    args0 = {
        "key1": "seago@seagosoft.com",
        "key2": "0851-123456789",
        "key3": "123456789"
    }

    rules = [
        r"key1 to_key1 ^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$",  # email
        r"key2 to_key2 ^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\s\./0-9]*$",  # phone
        r"key3 to_key3 ^[0-9]+$"  # numbers
    ]

    amt = ArgsMappingToken()

    print(amt.parsing_reg_rules(rules, args0))

    args1 = {
        "key1": "_seago^seagosoft.com",
        "key2": "0851-123456789a",
        "key3": "123456789a"
    }

    print(amt.parsing_reg_rules(rules, args1))
